package ademar.bitac.test.fixture

import ademar.bitac.viewmodel.WalletViewModel

object WalletViewModelFixture {

    val id = 123L
    val name = "A NAME"
    val address = "1DPYudPDKLxnFkTtDUbWrEZZhfbuHoWgX8"
    val balance = "456"
    val creation = "789"
    val edition = "123456"

    fun makeModel(customId: Long = id) = WalletViewModel(customId, name, address, balance, creation, edition)

}
