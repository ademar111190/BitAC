package ademar.bitac.injection

import ademar.bitac.ext.getApp
import android.app.Activity

object Injector {

    var component: (Activity) -> LifeCycleComponent = { activity ->
        DaggerLifeCycleComponent.builder()
                .lifeCycleModule(LifeCycleModule(activity))
                .appComponent(activity.getApp().module)
                .build()
    }

    fun get(activity: Activity) = component.invoke(activity)

}
