package ademar.bitac.interactor

import ademar.bitac.ext.KEY_PROVIDER_SETUP
import ademar.bitac.model.Currency
import ademar.bitac.model.Provider
import android.content.Context
import android.preference.PreferenceManager
import javax.inject.Inject

class DisableProvider @Inject constructor(
        context: Context,
        private val getEnabledProviders: GetEnabledProviders
) {

    var preference = PreferenceManager.getDefaultSharedPreferences(context)

    fun execute(currency: Currency, provider: Provider) = preference.edit()
            .putStringSet(KEY_PROVIDER_SETUP, getEnabledProviders.execute()
                    .toMutableList()
                    .apply { remove(currency to provider) }
                    .map { "${it.first.id}:${it.second.id}" }
                    .toSet())
            .apply()

}
