package ademar.bitac

import ademar.bitac.injection.AppComponent
import ademar.bitac.injection.AppModule
import ademar.bitac.injection.DaggerAppComponent
import android.app.Application

class App : Application() {

    val module: AppComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this, BuildConfig.BASE_API))
            .build()

}
