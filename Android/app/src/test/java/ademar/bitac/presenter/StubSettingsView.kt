package ademar.bitac.presenter

import ademar.bitac.model.Currency
import ademar.bitac.model.Provider
import junit.framework.Assert.fail

open class StubSettingsView : SettingsView {

    override fun addConversion(currency: Currency, provider: Provider) = fail("should not call trackAddConversion. currency: $currency, provider: $provider")
    override fun chooseCurrency(currencies: Map<String, Currency>) = fail("should not call chooseCurrency. currencies: $currencies")
    override fun chooseProvider(currency: Currency, providers: Map<String, Provider>) = fail("should not call chooseProvider. currency: $currency, providers: $providers")
    override fun showError(throwable: Throwable) = fail("should not call showError. throwable: $throwable")

}
