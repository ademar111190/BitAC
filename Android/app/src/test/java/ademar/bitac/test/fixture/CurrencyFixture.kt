package ademar.bitac.test.fixture

import ademar.bitac.model.Currency

object CurrencyFixture {

    const val id = "currency_id"
    const val name = "A name"
    const val symbol = "A symbol"

    fun makeModel() = Currency(id, name, symbol)

}
