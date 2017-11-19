package ademar.bitac.interactor

import ademar.bitac.R
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.support.annotation.StringRes
import android.widget.Toast
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CopyToClipboard @Inject constructor(
        private val context: Context
) {

    private val clipboard by lazy {
        context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    }

    fun execute(@StringRes label: Int, text: String?) {
        if (text != null) {
            clipboard.primaryClip = ClipData.newPlainText(context.getString(label), text)
            Toast.makeText(context, R.string.app_copied, Toast.LENGTH_SHORT).show()
        }
    }

}
