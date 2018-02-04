package ademar.bitac.injection

import ademar.bitac.interactor.*
import ademar.bitac.model.StandardErrors
import ademar.bitac.repository.WalletRepository
import ademar.bitac.repository.datasource.WalletCloud
import android.content.Context
import com.crashlytics.android.answers.Answers
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    val analytics: Analytics
    val answers: Answers
    val walletCloud: WalletCloud
    val context: Context
    val copyToClipboard: CopyToClipboard
    val deleteWallet: DeleteWallet
    val getTheme: GetTheme
    val walletRepository: WalletRepository
    val retrofit: Retrofit
    val standardErrors: StandardErrors
    val walletAddWatcher: WalletAddWatcher
    val walletChangeWatcher: WalletChangeWatcher
    val walletDeleteWatcher: WalletDeleteWatcher

}
