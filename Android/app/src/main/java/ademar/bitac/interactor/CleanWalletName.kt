package ademar.bitac.interactor

import ademar.bitac.R
import android.content.Context
import java.net.URLDecoder
import javax.inject.Inject

class CleanWalletName @Inject constructor(

        private val context: Context

) {

    fun execute(name: String?): String {
        return if (name == null || name.isBlank()) {
            context.getString(R.string.app_unnamed)
        } else {
            URLDecoder.decode(name, "UTF-8").trim()
        }
    }

}
