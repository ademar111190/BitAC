package ademar.bitac.interactor.wallet

import ademar.bitac.model.Address
import ademar.bitac.repository.WalletRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetAddressData @Inject constructor(

        private val walletRepository: WalletRepository

) {

    fun execute(address: String): Single<Address> {
        return walletRepository.fetchMultiAddress(address).filter { it ->
            it.addresses != null
        }.map {
                    it.addresses!!
                }.flatMapIterable {
                    it
                }.firstOrError()
    }

}
