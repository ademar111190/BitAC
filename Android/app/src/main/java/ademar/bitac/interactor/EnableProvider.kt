package ademar.bitac.interactor

import ademar.bitac.R
import ademar.bitac.ext.KEY_PROVIDER_SETUP
import ademar.bitac.model.Currency
import ademar.bitac.model.Provider
import android.content.Context
import android.preference.PreferenceManager
import javax.inject.Inject

class EnableProvider @Inject constructor(
        private val context: Context,
        private val getEnabledProviders: GetEnabledProviders,
        private val getProviders: GetProviders,
        private val getCurrencies: GetCurrencies
) {

    var preference = PreferenceManager.getDefaultSharedPreferences(context)

    fun execute(currency: Currency, provider: Provider) = preference.edit()
            .putStringSet(KEY_PROVIDER_SETUP, getEnabledProviders.execute()
                    .toMutableList()
                    .apply { add(currency to provider) }
                    .map { "${it.first.id}:${it.second.id}" }
                    .toSet())
            .apply()

    fun execute(): List<Pair<Currency, Provider>> {
        val defaultCurrency = getCurrencies.execute(context.getString(R.string.default_currency))
        val defaultProvider = getProviders.execute(context.getString(R.string.default_provider))
        val providers = listOf(defaultCurrency to defaultProvider)
        preference.edit()
                .putStringSet(KEY_PROVIDER_SETUP, providers
                        .map { "${it.first.id}:${it.second.id}" }
                        .toSet())
                .apply()
        return providers
    }

}
