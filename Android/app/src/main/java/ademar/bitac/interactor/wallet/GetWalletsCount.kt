package ademar.bitac.interactor.wallet

import ademar.bitac.repository.WalletRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetWalletsCount @Inject constructor(

        private val walletRepository: WalletRepository

) {

    fun execute(): Single<Int> {
        return Single.just(walletRepository.getWallets().size)
    }

}
