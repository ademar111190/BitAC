package ademar.bitac.interactor.provider

import ademar.bitac.model.Currency
import ademar.bitac.model.Provider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetProviders @Inject constructor(

        getCurrencies: GetCurrencies

) {

    private val cache = hashMapOf<Currency, List<Provider>>()
    private val providers = hashMapOf<String, Provider>()

    init {
        val bitfinex = Provider("bitfinex", "Bitfinex")
        val bitso = Provider("bitso", "Bitso")
        val foxbit = Provider("foxbit", "FoxBit")

        providers[bitfinex.id] = bitfinex
        providers[bitso.id] = bitso
        providers[foxbit.id] = foxbit

        cache[getCurrencies.execute("BRL")] = listOf(foxbit)
        cache[getCurrencies.execute("EUR")] = listOf(bitfinex)
        cache[getCurrencies.execute("MXN")] = listOf(bitso)
        cache[getCurrencies.execute("USD")] = listOf(bitfinex)
    }

    fun execute(currency: Currency) = cache[currency] ?: throw IllegalArgumentException("Unknown currency $currency")

    fun execute(id: String) = providers[id] ?: throw IllegalArgumentException("Unknown provider with id $id")

}
