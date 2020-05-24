package ademar.bitac.injection

import ademar.bitac.App
import ademar.bitac.BuildConfig
import ademar.bitac.ext.standardErrors
import ademar.bitac.model.StandardErrors
import ademar.bitac.repository.datasource.WalletCloud
import android.content.Context
import com.github.aurae.retrofit2.LoganSquareConverterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class AppModule(

        private val app: App,
        private val apiUrl: String

) {

    @Provides
    @Singleton
    fun provideContext(): Context {
        return app
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(httpLoggingInterceptor)
        }
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, standardErrors: StandardErrors): Retrofit {
        val retrofit = Retrofit.Builder()
                .baseUrl(apiUrl)
                .addConverterFactory(LoganSquareConverterFactory.create())
                .client(okHttpClient)
                .build()
        retrofit.standardErrors = standardErrors
        return retrofit
    }

    @Provides
    fun provideCloud(retrofit: Retrofit): WalletCloud {
        return retrofit.create(WalletCloud::class.java)
    }

}
