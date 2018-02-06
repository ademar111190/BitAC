package ademar.bitac.interactor.provider

import ademar.bitac.model.Currency
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class GetCurrenciesTest {

    private val real = Currency("BRL", "Real", "R$")
    private val euro = Currency("EUR", "Euro", "€")
    private val peso = Currency("MXN", "Peso", "$")
    private val dollar = Currency("USD", "Dollar", "$")

    @Test
    fun testExecute() {
        assertThat(GetCurrencies().execute()).containsExactly(real, euro, peso, dollar)
    }

    @Test
    fun testGetReal() {
        assertThat(GetCurrencies().execute("BRL")).isEqualTo(real)
    }

    @Test
    fun testGetEuro() {
        assertThat(GetCurrencies().execute("EUR")).isEqualTo(euro)
    }

    @Test
    fun testGetPeso() {
        assertThat(GetCurrencies().execute("MXN")).isEqualTo(peso)
    }

    @Test
    fun testGetDollar() {
        assertThat(GetCurrencies().execute("USD")).isEqualTo(dollar)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testGetUnknown() {
        GetCurrencies().execute("ABC")
    }

}
