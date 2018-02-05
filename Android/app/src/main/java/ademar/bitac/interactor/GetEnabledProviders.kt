package ademar.bitac.interactor

import ademar.bitac.ext.KEY_PROVIDER_FIRST_TIME
import ademar.bitac.ext.KEY_PROVIDER_SETUP
import android.content.Context
import android.preference.PreferenceManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetEnabledProviders @Inject constructor(
        private val context: Context,
        private val getProviders: GetProviders,
        private val getCurrencies: GetCurrencies
) {

    var preference = PreferenceManager.getDefaultSharedPreferences(context)

    fun execute() = if (preference.getBoolean(KEY_PROVIDER_FIRST_TIME, true)) {
        preference.edit()
                .putBoolean(KEY_PROVIDER_FIRST_TIME, false)
                .apply()
        EnableProvider(context, this, getProviders, getCurrencies).execute()
    } else {
        preference.getStringSet(KEY_PROVIDER_SETUP, setOf()).map {
            it.split(":")
        }.map { it[0] to it[1] }.map {
            getCurrencies.execute(it.first) to getProviders.execute(it.second)
        }
    }

}
