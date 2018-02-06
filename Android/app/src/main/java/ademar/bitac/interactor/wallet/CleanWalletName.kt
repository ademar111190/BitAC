package ademar.bitac.interactor.wallet

import java.net.URLDecoder
import javax.inject.Inject

class CleanWalletName @Inject constructor() {

    fun execute(name: String?, default: String): String {
        val decoded = URLDecoder.decode(name ?: "", "UTF-8").trim()
        return if (decoded.isBlank()) {
            default
        } else {
            decoded
        }
    }

}
