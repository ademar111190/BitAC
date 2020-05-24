package ademar.bitac.interactor.wallet

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
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
