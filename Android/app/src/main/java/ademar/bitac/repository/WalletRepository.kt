package ademar.bitac.repository

import ademar.bitac.interactor.Analytics
import ademar.bitac.interactor.WalletAddWatcher
import ademar.bitac.interactor.WalletChangeWatcher
import ademar.bitac.interactor.WalletDeleteWatcher
import ademar.bitac.model.Wallet
import ademar.bitac.repository.datasource.WalletLocal
import ademar.bitac.repository.datasource.WalletStorage
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WalletRepository @Inject constructor(

        private val walletLocal: WalletLocal,
        private val walletStorage: WalletStorage,
        walletAddWatcher: WalletAddWatcher,
        walletChangeWatcher: WalletChangeWatcher,
        walletDeleteWatcher: WalletDeleteWatcher,
        analytics: Analytics

) {

    init {
        walletAddWatcher.observe().subscribe({
            walletLocal.wallets?.put(it.id, it)
        }, analytics::trackError)
        walletChangeWatcher.observe().subscribe({
            walletLocal.wallets?.put(it.id, it)
        }, analytics::trackError)
        walletDeleteWatcher.observe().subscribe({
            walletLocal.wallets?.remove(it)
        }, analytics::trackError)
    }

    fun getWallets(): List<Wallet> {
        val cached = walletLocal.wallets?.values?.toList()
        return if (cached != null) {
            cached
        } else {
            val newCache = HashMap(walletStorage.getWallets().map { it.id to it }.toMap())
            walletLocal.wallets = newCache
            newCache.values.toList()
        }
    }

    fun addWallet(wallet: Wallet) {
        walletStorage.saveWallet(wallet)
    }

    fun updateWallet(wallet: Wallet): Wallet {
        val newWallet = wallet.copy(edition = System.nanoTime())
        walletStorage.saveWallet(newWallet)
        return newWallet
    }

    fun deleteWallet(id: Long) {
        walletStorage.deleteWallet(id)
    }

}
