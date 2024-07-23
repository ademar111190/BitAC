package ademar.bitac.test.fixture

import ademar.bitac.model.Wallet

object WalletFixture {

    const val id = 123L
    const val name = "A NAME"
    const val address = "bc1qm9n8x3jge2356hhyywfwrsmfczr49fxz37da8y"
    const val balance = 456L
    const val creation = 789L
    const val edition = 123456L

    fun makeModel(customId: Long = id) = Wallet(customId, name, address, balance, creation, edition)

}
