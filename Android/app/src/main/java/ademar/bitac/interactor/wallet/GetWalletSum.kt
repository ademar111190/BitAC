package ademar.bitac.interactor.wallet

import ademar.bitac.R
import ademar.bitac.ext.WALLET_SUM_ID
import ademar.bitac.model.Wallet
import ademar.bitac.repository.WalletRepository
import android.content.Context
import android.preference.PreferenceManager
import io.reactivex.rxjava3.core.Maybe
import javax.inject.Inject

class GetWalletSum @Inject constructor(

        private val context: Context,
        private val walletRepository: WalletRepository

) {

    private val preferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun execute(): Maybe<Wallet> {
        return if (preferences.getBoolean("sum_address", false)) {
            Maybe.create { emitter ->
                val wallet = Wallet(
                        id = WALLET_SUM_ID,
                        name = context.getString(R.string.settings_sum_address_title),
                        address = "",
                        balance = walletRepository.getWallets().sumOf { it.balance },
                        creation = 0,
                        edition = 0
                )
                if (!emitter.isDisposed) emitter.onSuccess(wallet)
            }
        } else {
            Maybe.empty()
        }
    }

}
