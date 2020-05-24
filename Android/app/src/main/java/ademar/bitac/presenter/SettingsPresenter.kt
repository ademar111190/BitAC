package ademar.bitac.presenter

import ademar.bitac.R
import ademar.bitac.interactor.provider.*
import ademar.bitac.model.Currency
import ademar.bitac.model.Provider
import ademar.bitac.navigation.Navigator
import ademar.bitac.view.Theme
import android.content.Context
import javax.inject.Inject

class SettingsPresenter @Inject constructor(

        private val context: Context,
        private val navigator: Navigator,
        private val getCurrencies: GetCurrencies,
        private val getProviders: GetProviders,
        private val getEnabledProviders: GetEnabledProviders,
        private val disableProvider: DisableProvider,
        private val enableProvider: EnableProvider

) {

    var view: SettingsView? = null

    fun loadData() {
        getEnabledProviders.execute().forEach {
            view?.addConversion(it.first, it.second)
        }
    }

    fun about() {
        navigator.launchAbout()
    }

    fun changeTheme(themeKey: String) {
        val theme = Theme.getTheme(themeKey)
        navigator.launchHome(theme)
    }

    fun addConversion() {
        val currencies = getCurrencies.execute().map { it.name to it }.toMap()
        view?.chooseCurrency(currencies)
    }

    fun addConversion(currency: Currency) {
        val providers = getProviders.execute(currency).map { it.name to it }.toMap()
        view?.chooseProvider(currency, providers)
    }

    fun addConversion(currency: Currency, provider: Provider) {
        if (getEnabledProviders.execute().none { it.first == currency && it.second == provider }) {
            enableProvider.execute(currency, provider)
            view?.addConversion(currency, provider)
        } else {
            val error = Exception(context.getString(R.string.settings_add_conversion_repeated, currency.name, provider.name))
            view?.showError(error)
        }
    }

    fun removeConversion(currency: Currency, provider: Provider) {
        disableProvider.execute(currency, provider)
    }

}
