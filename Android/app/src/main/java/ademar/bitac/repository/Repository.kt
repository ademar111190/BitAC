package ademar.bitac.repository

import ademar.bitac.interactor.Analytics
import ademar.bitac.interactor.WalletAddWatcher
import ademar.bitac.interactor.WalletChangeWatcher
import ademar.bitac.interactor.WalletDeleteWatcher
import ademar.bitac.model.Wallet
import ademar.bitac.repository.datasource.Local
import ademar.bitac.repository.datasource.Storage
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(

        private val local: Local,
        private val storage: Storage,
        walletAddWatcher: WalletAddWatcher,
        walletChangeWatcher: WalletChangeWatcher,
        walletDeleteWatcher: WalletDeleteWatcher,
        analytics: Analytics

) {

    init {
        walletAddWatcher.observe().subscribe({
            local.wallets?.put(it.id, it)
        }, analytics::trackError)
        walletChangeWatcher.observe().subscribe({
            local.wallets?.put(it.id, it)
        }, analytics::trackError)
        walletDeleteWatcher.observe().subscribe({
            local.wallets?.remove(it)
        }, analytics::trackError)
    }

    fun getWallets(): List<Wallet> {
        val cached = local.wallets?.values?.toList()
        return if (cached != null) {
            cached
        } else {
            val newCache = HashMap(storage.getWallets().map { it.id to it }.toMap())
            local.wallets = newCache
            newCache.values.toList()
        }
    }

    fun addWallet(wallet: Wallet) {
        storage.saveWallet(wallet)
    }

    fun updateWallet(wallet: Wallet): Wallet {
        val newWallet = wallet.copy(edition = System.nanoTime())
        storage.saveWallet(newWallet)
        return newWallet
    }

    fun deleteWallet(id: Long) {
        storage.deleteWallet(id)
    }

}
