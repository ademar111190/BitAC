package ademar.bitac.injection

import ademar.bitac.interactor.CopyToClipboard
import ademar.bitac.interactor.GetTheme
import ademar.bitac.interactor.provider.GetCurrencies
import ademar.bitac.interactor.provider.GetEnabledProviders
import ademar.bitac.interactor.provider.GetProviders
import ademar.bitac.interactor.wallet.WalletAddWatcher
import ademar.bitac.interactor.wallet.WalletChangeWatcher
import ademar.bitac.interactor.wallet.WalletDeleteWatcher
import ademar.bitac.model.StandardErrors
import ademar.bitac.repository.WalletRepository
import ademar.bitac.repository.datasource.WalletCloud
import android.content.Context
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    val walletCloud: WalletCloud
    val context: Context
    val copyToClipboard: CopyToClipboard
    val getTheme: GetTheme
    val getCurrencies: GetCurrencies
    val getEnabledProvider: GetEnabledProviders
    val getProviders: GetProviders
    val walletRepository: WalletRepository
    val retrofit: Retrofit
    val standardErrors: StandardErrors
    val walletAddWatcher: WalletAddWatcher
    val walletChangeWatcher: WalletChangeWatcher
    val walletDeleteWatcher: WalletDeleteWatcher

}
