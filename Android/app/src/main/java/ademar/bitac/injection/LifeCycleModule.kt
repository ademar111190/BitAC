package ademar.bitac.injection

import android.app.Activity
import dagger.Module
import dagger.Provides

@Module
class LifeCycleModule(

        private val activity: Activity?

) {

    @Provides
    fun provideActivity(): Activity {
        return activity!!
    }

}
