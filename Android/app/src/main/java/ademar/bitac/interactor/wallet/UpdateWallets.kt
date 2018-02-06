package ademar.bitac.interactor.wallet

import ademar.bitac.model.Wallet
import ademar.bitac.repository.WalletRepository
import io.reactivex.Observable
import javax.inject.Inject

class UpdateWallets @Inject constructor(

        private val walletRepository: WalletRepository,
        private val getWalletData: GetWalletData,
        private val walletChangeWatcher: WalletChangeWatcher

) {

    fun execute(): Observable<Wallet> {
        return Observable.fromIterable(walletRepository.getWallets())
                .flatMap { getWalletData.execute(it).toObservable() }
                .map { walletRepository.updateWallet(it) }
                .doOnNext { walletChangeWatcher.notifyDataChanged(it) }
    }

}
