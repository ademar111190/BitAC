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
        val address = BitcoinUri().getAddress("bc1qm9n8x3jge2356hhyywfwrsmfczr49fxz37da8y")
        assertThat(address).isEqualTo("bc1qm9n8x3jge2356hhyywfwrsmfczr49fxz37da8y")
    }

    @Test
    fun testGetAddressOnlyAddress() {
        val address = BitcoinUri().getAddress("bitcoin:bc1qm9n8x3jge2356hhyywfwrsmfczr49fxz37da8y")
        assertThat(address).isEqualTo("bc1qm9n8x3jge2356hhyywfwrsmfczr49fxz37da8y")
    }

    @Test
    fun testGetAddressOnlyAddressNewLine() {
        val address = BitcoinUri().getAddress("bitcoin:bc1qm9n8x3jge2356hhyywfwrsmfczr49fxz37da8y\n")
        assertThat(address).isEqualTo("bc1qm9n8x3jge2356hhyywfwrsmfczr49fxz37da8y")
    }

    @Test
    fun testGetAddressWithAmount() {
        val address = BitcoinUri().getAddress("bitcoin:bc1qm9n8x3jge2356hhyywfwrsmfczr49fxz37da8y?amount=1.2")
        assertThat(address).isEqualTo("bc1qm9n8x3jge2356hhyywfwrsmfczr49fxz37da8y")
    }

    @Test
    fun testGetAddressWithLabel() {
        val address = BitcoinUri().getAddress("bitcoin:bc1qm9n8x3jge2356hhyywfwrsmfczr49fxz37da8y?label=Satoshi")
        assertThat(address).isEqualTo("bc1qm9n8x3jge2356hhyywfwrsmfczr49fxz37da8y")
    }

    @Test
    fun testGetAddressComplete() {
        val address = BitcoinUri().getAddress("bitcoin:bc1qm9n8x3jge2356hhyywfwrsmfczr49fxz37da8y?amount=1.2&message=Payment&label=Satoshi&extra=other-param")
        assertThat(address).isEqualTo("bc1qm9n8x3jge2356hhyywfwrsmfczr49fxz37da8y")
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
        val label = BitcoinUri().getLabel("bitcoin:bc1qm9n8x3jge2356hhyywfwrsmfczr49fxz37da8y")
        assertThat(label).isNull()
    }

    @Test
    fun testGetLabelOnlyAddressNewLine() {
        val label = BitcoinUri().getLabel("bitcoin:bc1qm9n8x3jge2356hhyywfwrsmfczr49fxz37da8y\n")
        assertThat(label).isNull()
    }

    @Test
    fun testGetLabelWithAmount() {
        val label = BitcoinUri().getLabel("bitcoin:bc1qm9n8x3jge2356hhyywfwrsmfczr49fxz37da8y?amount=1.2")
        assertThat(label).isNull()
    }

    @Test
    fun testGetLabelWithLabel() {
        val label = BitcoinUri().getLabel("bitcoin:bc1qm9n8x3jge2356hhyywfwrsmfczr49fxz37da8y?label=Satoshi")
        assertThat(label).isEqualTo("Satoshi")
    }

    @Test
    fun testGetLabelComplete() {
        val label = BitcoinUri().getLabel("bitcoin:bc1qm9n8x3jge2356hhyywfwrsmfczr49fxz37da8y?amount=1.2&message=Payment&label=Satoshi&extra=other-param")
        assertThat(label).isEqualTo("Satoshi")
    }

}
