package ademar.bitac

import ademar.bitac.injection.AppComponent
import ademar.bitac.injection.AppModule
import ademar.bitac.injection.DaggerAppComponent
import android.app.Application
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.answers.Answers
import io.fabric.sdk.android.Fabric


class App : Application() {

    val module: AppComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this, BuildConfig.BASE_API))
            .build()

    override fun onCreate() {
        super.onCreate()
        Fabric.with(this, Crashlytics(), Answers())
    }

}
