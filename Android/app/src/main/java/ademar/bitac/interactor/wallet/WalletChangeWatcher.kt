package ademar.bitac.interactor.wallet

import ademar.bitac.model.Wallet
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WalletChangeWatcher @Inject constructor() {

    private val subscriber = PublishSubject.create<Wallet>()

    fun observe(): Observable<Wallet> {
        return subscriber
    }

    fun notifyDataChanged(wallet: Wallet) {
        subscriber.onNext(wallet)
    }

}
