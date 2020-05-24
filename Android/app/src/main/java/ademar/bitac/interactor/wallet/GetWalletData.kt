package ademar.bitac.interactor.wallet

import ademar.bitac.model.Wallet
import ademar.bitac.repository.WalletRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetWalletData @Inject constructor(

        private val walletRepository: WalletRepository

) {

    fun execute(wallet: Wallet): Single<Wallet> {
        return walletRepository.fetchMultiAddress(wallet.address).filter {
            it.addresses != null
        }.map {
            it.addresses!!
        }.flatMapIterable {
            it
        }.map {
            wallet.copy(balance = it.balance ?: 0L)
        }.firstOrError()
    }

}
