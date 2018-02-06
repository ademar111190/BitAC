package ademar.bitac.interactor.wallet

import ademar.bitac.repository.WalletRepository
import io.reactivex.Completable
import javax.inject.Inject

class DeleteWallet @Inject constructor(

        private val walletRepository: WalletRepository,
        private val walletDeleteWatcher: WalletDeleteWatcher

) {

    fun execute(id: Long): Completable {
        return Completable.fromAction {
            walletRepository.deleteWallet(id)
            walletDeleteWatcher.notifyDataDeleted(id)
        }
    }

}
