package ademar.bitac.repository

import ademar.bitac.ext.observeBody
import ademar.bitac.ext.subscribeBy
import ademar.bitac.interactor.wallet.WalletAddWatcher
import ademar.bitac.interactor.wallet.WalletChangeWatcher
import ademar.bitac.interactor.wallet.WalletDeleteWatcher
import ademar.bitac.model.MultiAddress
import ademar.bitac.model.Wallet
import ademar.bitac.repository.datasource.WalletCloud
import ademar.bitac.repository.datasource.WalletLocal
import ademar.bitac.repository.datasource.WalletStorage
import io.reactivex.rxjava3.core.Observable
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WalletRepository @Inject constructor(

        private val walletLocal: WalletLocal,
        private val walletStorage: WalletStorage,
        private val walletCloud: WalletCloud,
        private val retrofit: Retrofit,
        walletAddWatcher: WalletAddWatcher,
        walletChangeWatcher: WalletChangeWatcher,
        walletDeleteWatcher: WalletDeleteWatcher

) {

    init {
        walletAddWatcher.observe().subscribeBy({
            walletLocal.wallets?.put(it.id, it)
        })
        walletChangeWatcher.observe().subscribeBy({
            walletLocal.wallets?.put(it.id, it)
        })
        walletDeleteWatcher.observe().subscribeBy({
            walletLocal.wallets?.remove(it)
        })
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

    fun fetchMultiAddress(address: String): Observable<MultiAddress> {
        return Observable.fromCallable {
            walletCloud.getAddressBalances(address)
        }.flatMap {
            retrofit.observeBody(it)
        }
    }

}
