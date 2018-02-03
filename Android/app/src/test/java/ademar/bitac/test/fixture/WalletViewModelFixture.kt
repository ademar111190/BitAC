package ademar.bitac.test.fixture

import ademar.bitac.viewmodel.WalletViewModel

object WalletViewModelFixture {

    const val id = 123L
    const val name = "A NAME"
    const val address = "1DPYudPDKLxnFkTtDUbWrEZZhfbuHoWgX8"
    const val balance = "456"
    const val creation = "789"
    const val edition = "123456"

    fun makeModel(customId: Long = id) = WalletViewModel(customId, name, address, balance, creation, edition)

}
