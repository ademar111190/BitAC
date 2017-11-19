package ademar.bitac.interactor

import ademar.bitac.repository.Repository
import io.reactivex.Single
import javax.inject.Inject

class GetWalletsCount @Inject constructor(

        private val repository: Repository

) {

    fun execute(): Single<Int> {
        return Single.just(repository.getWallets().size)
    }

}
