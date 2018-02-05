package ademar.bitac.view

import ademar.bitac.R
import ademar.bitac.injection.Injector
import ademar.bitac.interactor.*
import ademar.bitac.model.Currency
import ademar.bitac.model.Provider
import ademar.bitac.navigation.Navigator
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.preference.Preference
import android.preference.PreferenceCategory
import android.preference.PreferenceFragment
import android.widget.Toast
import javax.inject.Inject

class SettingsFragment : PreferenceFragment() {

    @Inject lateinit var navigator: Navigator
    @Inject lateinit var analytics: Analytics
    @Inject lateinit var getCurrencies: GetCurrencies
    @Inject lateinit var getProviders: GetProviders
    @Inject lateinit var getEnabledProviders: GetEnabledProviders
    @Inject lateinit var disableProvider: DisableProvider
    @Inject lateinit var enableProvider: EnableProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.preferences)
        Injector.get(activity).inject(this)

        findPreference("about").setOnPreferenceClickListener {
            analytics.trackAbout()
            navigator.launchAbout()
            true
        }

        findPreference("theme").setOnPreferenceChangeListener { preference, value ->
            when {
                preference.key == "theme" && value is String -> {
                    val theme = Theme.getTheme(value)
                    analytics.trackThemeChange(theme)
                    navigator.launchHome(theme)
                }
                else -> null
            } != null
        }

        val conversions = findPreference("conversions") as PreferenceCategory
        val context = conversions.context
        getEnabledProviders.execute().forEach {
            addConversion(context, conversions, it.first, it.second)
        }

        findPreference("add_conversion").setOnPreferenceClickListener {
            val currencies = getCurrencies.execute().map { it.name to it }.toMap()
            AlertDialog.Builder(context)
                    .setTitle(R.string.settings_add_conversion_step_1)
                    .setNegativeButton(R.string.settings_add_conversion_cancel, null)
                    .setItems(currencies.keys.toTypedArray(), { currencyDialog, currencyWhich ->
                        currencyDialog.dismiss()
                        val currency = currencies[currencies.keys.elementAt(currencyWhich)]
                        if (currency != null) {
                            val providers = getProviders.execute(currency).map { it.name to it }.toMap()
                            AlertDialog.Builder(context)
                                    .setTitle(R.string.settings_add_conversion_step_2)
                                    .setNegativeButton(R.string.settings_add_conversion_cancel, null)
                                    .setItems(providers.keys.toTypedArray(), { providerDialog, providerWhich ->
                                        providerDialog.dismiss()
                                        val provider = providers[providers.keys.elementAt(providerWhich)]
                                        if (provider != null) {
                                            if (getEnabledProviders.execute().none { it.first == currency && it.second == provider }) {
                                                enableProvider.execute(currency, provider)
                                                addConversion(context, conversions, currency, provider)
                                            } else {
                                                val message = context.getString(R.string.settings_add_conversion_repeated, currency.name, provider.name)
                                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                                            }
                                        } else {
                                            analytics.trackError(IllegalStateException("Unknown provider to position $providerWhich input $providers"))
                                        }
                                    })
                                    .create()
                                    .show()
                        } else {
                            analytics.trackError(IllegalStateException("Unknown currency to position $currencyWhich input $currencies"))
                        }
                    })
                    .create()
                    .show()
            true
        }
    }

    private fun addConversion(context: Context, conversions: PreferenceCategory, currency: Currency, provider: Provider) {
        conversions.addPreference(Preference(context).apply {
            title = context.getString(R.string.settings_using_conversion, currency.name, provider.name)
            setOnPreferenceClickListener {
                AlertDialog.Builder(context)
                        .setTitle(R.string.settings_delete_conversion_title)
                        .setMessage(context.getString(R.string.settings_delete_conversion_message, currency.name, provider.name))
                        .setNegativeButton(R.string.settings_delete_conversion_cancel, null)
                        .setPositiveButton(R.string.settings_delete_conversion_delete, { _, _ ->
                            disableProvider.execute(currency, provider)
                            conversions.removePreference(this)
                        })
                        .create()
                        .show()
                true
            }
        })
    }

}
