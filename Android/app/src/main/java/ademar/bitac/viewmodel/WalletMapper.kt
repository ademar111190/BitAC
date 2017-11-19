package ademar.bitac.viewmodel

import ademar.bitac.model.Address
import ademar.bitac.model.Wallet
import android.content.Context
import android.text.format.DateFormat
import java.text.DecimalFormat
import java.util.*
import javax.inject.Inject

class WalletMapper @Inject constructor(

        context: Context

) {

    private val pattern = "0.00000000 BTC"
    private val base = 100000000.0
    private val dateFormat = DateFormat.getDateFormat(context)

    fun transform(wallet: Wallet): WalletViewModel {
        val creation = dateFormat.format(Date(wallet.creation))
        val edition = dateFormat.format(Date(wallet.edition))
        val balance = DecimalFormat(pattern).format(wallet.balance.toDouble() / base)
        return WalletViewModel(wallet.id, wallet.name, wallet.address, balance, creation, edition)
    }

    fun transform(viewModel: WalletViewModel, address: Address): WalletViewModel {
        val time = System.nanoTime()
        val creation = dateFormat.format(time)
        val edition = dateFormat.format(time)
        val balance = DecimalFormat(pattern).format((address.balance?.toDouble() ?: 0.0) / base)
        val newAddress = address.address ?: viewModel.address
        return viewModel.copy(address = newAddress, balance = balance, creation = creation, edition = edition)
    }

    fun transform(viewModel: WalletViewModel): Wallet {
        val creation = dateFormat.parse(viewModel.creation).time
        val edition = dateFormat.parse(viewModel.edition).time
        val balance = (DecimalFormat(pattern).parse(viewModel.balance).toDouble() * base).toLong()
        return Wallet(viewModel.walletId, viewModel.name, viewModel.address, balance, creation, edition)
    }

}
