package ademar.bitac.test.fixture

import ademar.bitac.viewmodel.WalletViewModel

object WalletViewModelFixture {

    const val id = 123L
    const val name = "A NAME"
    const val address = "bc1qm9n8x3jge2356hhyywfwrsmfczr49fxz37da8y"
    const val balance = "456"
    const val creation = "789"
    const val edition = "123456"

    fun makeModel(customId: Long = id) = WalletViewModel(customId, name, address, balance, creation, edition)

}
