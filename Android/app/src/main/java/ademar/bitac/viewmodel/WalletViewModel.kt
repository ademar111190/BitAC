package ademar.bitac.viewmodel

data class WalletViewModel(
        val walletId: Long,
        val name: String,
        val address: String,
        val balance: String,
        val creation: String,
        val edition: String)
