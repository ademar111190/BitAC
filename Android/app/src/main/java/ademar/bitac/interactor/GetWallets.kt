package ademar.bitac.interactor

import ademar.bitac.model.Wallet
import ademar.bitac.repository.Repository
import io.reactivex.Observable
import javax.inject.Inject

class GetWallets @Inject constructor(

        private val repository: Repository

) {

    fun execute(): Observable<Wallet> {
        return Observable.fromIterable(repository.getWallets())
    }

}
