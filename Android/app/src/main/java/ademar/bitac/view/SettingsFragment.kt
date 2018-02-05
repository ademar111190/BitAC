package ademar.bitac.view

import ademar.bitac.R
import ademar.bitac.injection.Injector
import ademar.bitac.interactor.Analytics
import ademar.bitac.model.Currency
import ademar.bitac.model.Provider
import ademar.bitac.presenter.SettingsPresenter
import ademar.bitac.presenter.SettingsView
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.preference.Preference
import android.preference.PreferenceCategory
import android.preference.PreferenceFragment
import android.widget.Toast
import javax.inject.Inject

class SettingsFragment : PreferenceFragment(), SettingsView {

    @Inject lateinit var analytics: Analytics
    @Inject lateinit var presenter: SettingsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.preferences)
        Injector.get(activity).inject(this)

        presenter.view = this
        presenter.loadData()

        findPreference("about").setOnPreferenceClickListener {
            presenter.about()
            true
        }

        findPreference("theme").setOnPreferenceChangeListener { preference, value ->
            when {
                preference.key == "theme" && value is String -> presenter.changeTheme(value)
                else -> null
            } != null
        }

        findPreference("add_conversion").setOnPreferenceClickListener {
            presenter.addConversion()
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
                            presenter.removeConversion(currency, provider)
                            conversions.removePreference(this)
                        })
                        .create()
                        .show()
                true
            }
        })
    }

    override fun addConversion(currency: Currency, provider: Provider) {
        val conversions = findPreference("conversions") as PreferenceCategory
        val context = conversions.context
        addConversion(context, conversions, currency, provider)
    }

    override fun chooseCurrency(currencies: Map<String, Currency>) {
        val conversions = findPreference("conversions") as PreferenceCategory
        val context = conversions.context
        AlertDialog.Builder(context)
                .setTitle(R.string.settings_add_conversion_step_1)
                .setNegativeButton(R.string.settings_add_conversion_cancel, null)
                .setItems(currencies.keys.toTypedArray(), { currencyDialog, currencyWhich ->
                    currencyDialog.dismiss()
                    val currency = currencies[currencies.keys.elementAt(currencyWhich)]
                    if (currency != null) {
                        presenter.addConversion(currency)
                    } else {
                        analytics.trackError(IllegalStateException("Unknown currency to position $currencyWhich input $currencies"))
                    }
                })
                .create()
                .show()
    }

    override fun chooseProvider(currency: Currency, providers: Map<String, Provider>) {
        val conversions = findPreference("conversions") as PreferenceCategory
        val context = conversions.context
        AlertDialog.Builder(context)
                .setTitle(R.string.settings_add_conversion_step_2)
                .setNegativeButton(R.string.settings_add_conversion_cancel, null)
                .setItems(providers.keys.toTypedArray(), { providerDialog, providerWhich ->
                    providerDialog.dismiss()
                    val provider = providers[providers.keys.elementAt(providerWhich)]
                    if (provider != null) {
                        presenter.addConversion(currency, provider)
                    } else {
                        analytics.trackError(IllegalStateException("Unknown provider to position $providerWhich input $providers"))
                    }
                })
                .create()
                .show()
    }

    override fun showError(throwable: Throwable) {
        Toast.makeText(activity, throwable.message, Toast.LENGTH_SHORT).show()
    }

}
