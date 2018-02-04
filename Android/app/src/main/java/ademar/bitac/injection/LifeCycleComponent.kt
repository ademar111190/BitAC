package ademar.bitac.injection

import ademar.bitac.view.*
import dagger.Component

@LifeCycleScope
@Component(modules = [LifeCycleModule::class], dependencies = [AppComponent::class])
interface LifeCycleComponent {

    fun inject(obj: CheckAddressActivity)
    fun inject(obj: HomeActivity)
    fun inject(obj: SettingsFragment)
    fun inject(obj: StartActivity)
    fun inject(obj: WalletViewHolder)

}
