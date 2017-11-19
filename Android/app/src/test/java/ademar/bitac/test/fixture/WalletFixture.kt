package ademar.bitac.test.fixture

import ademar.bitac.model.Wallet

object WalletFixture {

    val id = 123L
    val name = "A NAME"
    val address = "1DPYudPDKLxnFkTtDUbWrEZZhfbuHoWgX8"
    val balance = 456L
    val creation = 789L
    val edition = 123456L

    fun makeModel(customId: Long = id) = Wallet(customId, name, address, balance, creation, edition)

}
