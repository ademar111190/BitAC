package ademar.bitac.interactor.wallet

import ademar.bitac.model.Wallet
import ademar.bitac.repository.WalletRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetWallets @Inject constructor(

        private val walletRepository: WalletRepository

) {

    fun execute(): Observable<Wallet> {
        return Observable.fromIterable(walletRepository.getWallets())
    }

}
