package ademar.bitac.test.fixture

import ademar.bitac.model.Wallet

object WalletFixture {

    const val id = 123L
    const val name = "A NAME"
    const val address = "1DPYudPDKLxnFkTtDUbWrEZZhfbuHoWgX8"
    const val balance = 456L
    const val creation = 789L
    const val edition = 123456L

    fun makeModel(customId: Long = id) = Wallet(customId, name, address, balance, creation, edition)

}
