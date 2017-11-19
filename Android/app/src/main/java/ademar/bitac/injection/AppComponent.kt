package ademar.bitac.injection

import ademar.bitac.interactor.*
import ademar.bitac.model.StandardErrors
import ademar.bitac.repository.Repository
import ademar.bitac.repository.datasource.Cloud
import android.content.Context
import com.crashlytics.android.answers.Answers
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {

    val analytics: Analytics
    val answers: Answers
    val cloud: Cloud
    val context: Context
    val copyToClipboard: CopyToClipboard
    val deleteWallet: DeleteWallet
    val getTheme: GetTheme
    val repository: Repository
    val retrofit: Retrofit
    val setTheme: SetTheme
    val standardErrors: StandardErrors
    val walletAddWatcher: WalletAddWatcher
    val walletChangeWatcher: WalletChangeWatcher
    val walletDeleteWatcher: WalletDeleteWatcher

}
