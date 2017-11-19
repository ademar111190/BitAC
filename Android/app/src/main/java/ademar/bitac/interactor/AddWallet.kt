package ademar.bitac.interactor

import ademar.bitac.model.Wallet
import ademar.bitac.repository.Repository
import io.reactivex.Completable
import javax.inject.Inject

class AddWallet @Inject constructor(

        private val repository: Repository,
        private val walletAddWatcher: WalletAddWatcher

) {

    fun execute(name: String, address: String, balance: Long): Completable {
        return Completable.fromAction {
            val time = System.nanoTime()
            val wallet = Wallet(time, name, address, balance, time, time)
            repository.addWallet(wallet)
            walletAddWatcher.notifyDataAdded(wallet)
        }
    }

    fun execute(wallet: Wallet): Completable {
        return Completable.fromAction {
            repository.addWallet(wallet)
            walletAddWatcher.notifyDataAdded(wallet)
        }
    }

}
