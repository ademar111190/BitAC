package ademar.bitac.ext

import android.view.inputmethod.EditorInfo
import android.widget.EditText

fun EditText.setActionNext(listener: () -> Unit) {
    setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_NEXT) {
            listener()
            true
        } else {
            false
        }
    }
}

fun EditText.setActionSend(listener: () -> Unit) {
    setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_SEND) {
            listener()
            true
        } else {
            false
        }
    }
}
