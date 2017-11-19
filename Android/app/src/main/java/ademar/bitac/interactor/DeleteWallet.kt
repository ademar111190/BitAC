package ademar.bitac.interactor

import ademar.bitac.repository.Repository
import io.reactivex.Completable
import javax.inject.Inject

class DeleteWallet @Inject constructor(

        private val repository: Repository,
        private val walletDeleteWatcher: WalletDeleteWatcher

) {

    fun execute(id: Long): Completable {
        return Completable.fromAction {
            repository.deleteWallet(id)
            walletDeleteWatcher.notifyDataDeleted(id)
        }
    }

}
