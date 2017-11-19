package ademar.bitac.model

import ademar.bitac.R
import android.content.Context
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StandardErrors @Inject constructor(

        private val context: Context

) {

    val unknown by lazy { Error(context.getString(R.string.error_message_unknown), 10_001) }
    val unauthorized by lazy { Error(context.getString(R.string.error_message_unauthorized), 10_002) }
    val noConnection by lazy { Error(context.getString(R.string.error_message_no_connection), 10_003) }

    fun humanReadableMessage(e: Throwable?): Throwable {
        return if (e == null || e.localizedMessage == null || e.localizedMessage.isBlank()) {
            unknown
        } else {
            e
        }
    }

}
