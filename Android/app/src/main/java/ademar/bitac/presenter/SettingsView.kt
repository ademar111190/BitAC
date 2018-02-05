package ademar.bitac.presenter

import ademar.bitac.model.Currency
import ademar.bitac.model.Provider

interface SettingsView {

    fun addConversion(currency: Currency, provider: Provider)

    fun chooseCurrency(currencies: Map<String, Currency>)

    fun chooseProvider(currency: Currency, providers: Map<String, Provider>)

    fun showError(throwable: Throwable)

}
