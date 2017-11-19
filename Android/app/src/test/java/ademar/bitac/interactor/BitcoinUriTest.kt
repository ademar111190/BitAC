package ademar.bitac.interactor

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class BitcoinUriTest {

    @Test
    fun testGetAddressNull() {
        val address = BitcoinUri().getAddress(null)
        assertThat(address).isNull()
    }

    @Test
    fun testGetAddressEmpty() {
        val address = BitcoinUri().getAddress("")
        assertThat(address).isNull()
    }

    @Test
    fun testGetAddressInvalid() {
        val address = BitcoinUri().getAddress("http://invalid.uri/bitcoin")
        assertThat(address).isNull()
    }

    @Test
    fun testGetAddressNoUri() {
        val address = BitcoinUri().getAddress("1DPYudPDKLxnFkTtDUbWrEZZhfbuHoWgX8")
        assertThat(address).isEqualTo("1DPYudPDKLxnFkTtDUbWrEZZhfbuHoWgX8")
    }

    @Test
    fun testGetAddressOnlyAddress() {
        val address = BitcoinUri().getAddress("bitcoin:1DPYudPDKLxnFkTtDUbWrEZZhfbuHoWgX8")
        assertThat(address).isEqualTo("1DPYudPDKLxnFkTtDUbWrEZZhfbuHoWgX8")
    }

    @Test
    fun testGetAddressOnlyAddressNewLine() {
        val address = BitcoinUri().getAddress("bitcoin:1DPYudPDKLxnFkTtDUbWrEZZhfbuHoWgX8\n")
        assertThat(address).isEqualTo("1DPYudPDKLxnFkTtDUbWrEZZhfbuHoWgX8")
    }

    @Test
    fun testGetAddressWithAmount() {
        val address = BitcoinUri().getAddress("bitcoin:1DPYudPDKLxnFkTtDUbWrEZZhfbuHoWgX8?amount=1.2")
        assertThat(address).isEqualTo("1DPYudPDKLxnFkTtDUbWrEZZhfbuHoWgX8")
    }

    @Test
    fun testGetAddressWithLabel() {
        val address = BitcoinUri().getAddress("bitcoin:1DPYudPDKLxnFkTtDUbWrEZZhfbuHoWgX8?label=Satoshi")
        assertThat(address).isEqualTo("1DPYudPDKLxnFkTtDUbWrEZZhfbuHoWgX8")
    }

    @Test
    fun testGetAddressComplete() {
        val address = BitcoinUri().getAddress("bitcoin:1DPYudPDKLxnFkTtDUbWrEZZhfbuHoWgX8?amount=1.2&message=Payment&label=Satoshi&extra=other-param")
        assertThat(address).isEqualTo("1DPYudPDKLxnFkTtDUbWrEZZhfbuHoWgX8")
    }

    @Test
    fun testGetLabelNull() {
        val label = BitcoinUri().getLabel(null)
        assertThat(label).isNull()
    }

    @Test
    fun testGetLabelEmpty() {
        val label = BitcoinUri().getLabel("")
        assertThat(label).isNull()
    }

    @Test
    fun testGetLabelInvalid() {
        val label = BitcoinUri().getLabel("http://invalid.uri/bitcoin")
        assertThat(label).isNull()
    }

    @Test
    fun testGetLabelOnlyAddress() {
        val label = BitcoinUri().getLabel("bitcoin:1DPYudPDKLxnFkTtDUbWrEZZhfbuHoWgX8")
        assertThat(label).isNull()
    }

    @Test
    fun testGetLabelOnlyAddressNewLine() {
        val label = BitcoinUri().getLabel("bitcoin:1DPYudPDKLxnFkTtDUbWrEZZhfbuHoWgX8\n")
        assertThat(label).isNull()
    }

    @Test
    fun testGetLabelWithAmount() {
        val label = BitcoinUri().getLabel("bitcoin:1DPYudPDKLxnFkTtDUbWrEZZhfbuHoWgX8?amount=1.2")
        assertThat(label).isNull()
    }

    @Test
    fun testGetLabelWithLabel() {
        val label = BitcoinUri().getLabel("bitcoin:1DPYudPDKLxnFkTtDUbWrEZZhfbuHoWgX8?label=Satoshi")
        assertThat(label).isEqualTo("Satoshi")
    }

    @Test
    fun testGetLabelComplete() {
        val label = BitcoinUri().getLabel("bitcoin:1DPYudPDKLxnFkTtDUbWrEZZhfbuHoWgX8?amount=1.2&message=Payment&label=Satoshi&extra=other-param")
        assertThat(label).isEqualTo("Satoshi")
    }

}
