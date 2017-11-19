package ademar.bitac.test.fixture

import ademar.bitac.model.Address

object AddressFixture {

    val address = "1DPYudPDKLxnFkTtDUbWrEZZhfbuHoWgX8"
    val balance = 123456789L

    fun makeModel() = Address().apply {
        address = AddressFixture.address
        balance = AddressFixture.balance
    }

}
