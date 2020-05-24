package ademar.bitac.ext

import io.reactivex.rxjava3.annotations.CheckReturnValue
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.Disposable

/**
 * Get ride of SAM conversion issues
 */

@CheckReturnValue
fun <T> Observable<T>.subscribeBy(
        onNext: (T) -> Unit = {},
        onError: (Throwable) -> Unit = { errorLogger(it) },
        onComplete: () -> Unit = {}
): Disposable = subscribe(onNext, onError, onComplete)

@CheckReturnValue
fun <T> Single<T>.subscribeBy(
        onSuccess: (T) -> Unit = {},
        onError: (Throwable) -> Unit = { errorLogger(it) }
): Disposable = subscribe(onSuccess, onError)

@CheckReturnValue
fun Completable.subscribeBy(
        onComplete: () -> Unit = {},
        onError: (Throwable) -> Unit = { errorLogger(it) }
): Disposable = subscribe(onComplete, onError)
