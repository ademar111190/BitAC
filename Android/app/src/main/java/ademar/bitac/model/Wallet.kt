package ademar.bitac.model

data class Wallet(
        val id: Long,
        val name: String,
        val address: String,
        val balance: Long,
        val creation: Long,
        val edition: Long)
