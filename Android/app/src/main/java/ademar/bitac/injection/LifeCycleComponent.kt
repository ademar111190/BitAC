package ademar.bitac.injection

import ademar.bitac.view.CheckAddressActivity
import ademar.bitac.view.HomeActivity
import ademar.bitac.view.StartActivity
import ademar.bitac.view.WalletViewHolder
import dagger.Component

@LifeCycleScope
@Component(modules = arrayOf(LifeCycleModule::class), dependencies = arrayOf(AppComponent::class))
interface LifeCycleComponent {

    fun inject(obj: CheckAddressActivity)
    fun inject(obj: HomeActivity)
    fun inject(obj: StartActivity)
    fun inject(obj: WalletViewHolder)

}
