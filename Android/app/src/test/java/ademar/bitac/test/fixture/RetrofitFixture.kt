package ademar.bitac.test.fixture

import ademar.bitac.ext.standardErrors
import ademar.bitac.model.StandardErrors
import android.content.Context
import com.github.aurae.retrofit2.LoganSquareConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit

object RetrofitFixture {

    fun makeRetrofit(context: Context, mockWebServer: MockWebServer): Retrofit {
        val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor())
                .build()
        val retrofit = Retrofit.Builder()
                .baseUrl(mockWebServer.url(""))
                .addConverterFactory(LoganSquareConverterFactory.create())
                .client(okHttpClient)
                .build()
        retrofit.standardErrors = StandardErrors(context)
        return retrofit
    }

}
