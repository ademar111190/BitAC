package ademar.bitac.interactor

import ademar.bitac.ext.observeBody
import ademar.bitac.model.Address
import ademar.bitac.repository.datasource.WalletCloud
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Retrofit
import javax.inject.Inject

class GetAddressData @Inject constructor(

        private val walletCloud: WalletCloud,
        private val retrofit: Retrofit

) {

    fun execute(address: String): Single<Address> {
        return Observable.fromCallable {
            walletCloud.getAddressBalances(address)
        }.flatMap {
            retrofit.observeBody(it)
        }.filter {
            it.addresses != null
        }.map {
            it.addresses!!
        }.flatMapIterable {
            it
        }.firstOrError()
    }

}
