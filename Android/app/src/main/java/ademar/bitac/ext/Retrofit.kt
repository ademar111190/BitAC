package ademar.bitac.ext

import ademar.bitac.model.FieldProperty
import ademar.bitac.model.StandardErrors
import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import retrofit2.Retrofit
import java.io.IOException

var Retrofit.standardErrors: StandardErrors by FieldProperty()

fun <T : Any> Retrofit.observeBody(call: Call<T>): Observable<T> = Observable.create {
    val response = try {
        call.execute()
    } catch (e: IOException) {
        if (!it.isDisposed) {
            it.onError(standardErrors.noConnection)
        } else {
            it.onComplete()
        }
        return@create
    }

    if (response != null) {
        when (response.code()) {
            200 -> {
                val body = response.body()
                if (body != null) {
                    it.onNext(body)
                    it.onComplete()
                } else {
                    it.onError(standardErrors.unknown)
                }
            }

            401 -> it.onError(standardErrors.unauthorized)
            500 -> {
                val errorMessage = response.errorBody()?.string()
                it.onError(if (errorMessage != null) Error(errorMessage) else standardErrors.unknown)
            }

            else -> {
                val errorBody = response.errorBody()
                if (errorBody != null) {
                    val converter =
                        responseBodyConverter<Error>(Error::class.java, arrayOf<Annotation>())
                    val error: Error? = converter.convert(errorBody)
                    it.onError(if (error?.message?.isNotEmpty() == true) error else standardErrors.unknown)
                } else {
                    it.onError(standardErrors.unknown)
                }
            }
        }
    }
}
