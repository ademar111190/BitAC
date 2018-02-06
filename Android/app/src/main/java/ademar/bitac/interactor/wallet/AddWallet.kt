package ademar.bitac.interactor.wallet

import ademar.bitac.model.Wallet
import ademar.bitac.repository.WalletRepository
import io.reactivex.Completable
import javax.inject.Inject

class AddWallet @Inject constructor(

        private val walletRepository: WalletRepository,
        private val walletAddWatcher: WalletAddWatcher

) {

    fun execute(name: String, address: String, balance: Long): Completable {
        return Completable.fromAction {
            val time = System.nanoTime()
            val wallet = Wallet(time, name, address, balance, time, time)
            walletRepository.addWallet(wallet)
            walletAddWatcher.notifyDataAdded(wallet)
        }
    }

    fun execute(wallet: Wallet): Completable {
        return Completable.fromAction {
            walletRepository.addWallet(wallet)
            walletAddWatcher.notifyDataAdded(wallet)
        }
    }

}
