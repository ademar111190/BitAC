package ademar.bitac.interactor

import ademar.bitac.model.Wallet
import ademar.bitac.repository.Repository
import io.reactivex.Observable
import javax.inject.Inject

class UpdateWallets @Inject constructor(

        private val repository: Repository,
        private val getWalletData: GetWalletData,
        private val walletChangeWatcher: WalletChangeWatcher

) {

    fun execute(): Observable<Wallet> {
        return Observable.fromIterable(repository.getWallets())
                .flatMap { getWalletData.execute(it).toObservable() }
                .map { repository.updateWallet(it) }
                .doOnNext { walletChangeWatcher.notifyDataChanged(it) }
    }

}
