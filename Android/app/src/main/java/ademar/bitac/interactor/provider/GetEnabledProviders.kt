package ademar.bitac.interactor.provider

import ademar.bitac.ext.KEY_PROVIDER_FIRST_TIME
import ademar.bitac.ext.KEY_PROVIDER_SETUP
import android.content.Context
import android.preference.PreferenceManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetEnabledProviders @Inject constructor(
        context: Context,
        private val getProviders: GetProviders,
        private val getCurrencies: GetCurrencies
) {

    var preference = PreferenceManager.getDefaultSharedPreferences(context)
    var enabledProvider = EnableProvider(context, this, getCurrencies, getProviders)

    fun execute() = if (preference.getBoolean(KEY_PROVIDER_FIRST_TIME, true)) {
        preference.edit()
                .putBoolean(KEY_PROVIDER_FIRST_TIME, false)
                .apply()
        enabledProvider.execute()
    } else {
        (preference.getStringSet(KEY_PROVIDER_SETUP, setOf()) ?: emptySet()).map {
            it.split(":")
        }.map { getCurrencies.execute(it[0]) to getProviders.execute(it[1]) }.toSet()
    }

}
