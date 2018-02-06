package ademar.bitac.interactor.provider

import ademar.bitac.model.Currency
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCurrencies @Inject constructor() {

    private val currencies = mapOf(
            "BRL" to Currency("BRL", "Real", "R$"),
            "EUR" to Currency("EUR", "Euro", "€"),
            "MXN" to Currency("MXN", "Peso", "$"),
            "USD" to Currency("USD", "Dollar", "$"))

    fun execute() = currencies.values

    fun execute(id: String) = currencies[id] ?: throw IllegalArgumentException("Unknown currency with id $id")

}
