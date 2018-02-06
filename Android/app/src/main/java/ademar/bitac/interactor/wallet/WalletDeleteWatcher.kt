package ademar.bitac.interactor.wallet

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WalletDeleteWatcher @Inject constructor() {

    private val subscriber = PublishSubject.create<Long>()

    fun observe(): Observable<Long> {
        return subscriber
    }

    fun notifyDataDeleted(walletId: Long) {
        subscriber.onNext(walletId)
    }

}
