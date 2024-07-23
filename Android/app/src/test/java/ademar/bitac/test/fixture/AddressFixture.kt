package ademar.bitac.test.fixture

import ademar.bitac.model.Address

object AddressFixture {

    const val address = "bc1qm9n8x3jge2356hhyywfwrsmfczr49fxz37da8y"
    const val balance = 123456789L

    fun makeModel() = Address().apply {
        address = AddressFixture.address
        balance = AddressFixture.balance
    }

}
