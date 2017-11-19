package ademar.bitac.sqlite

import android.content.ContentValues
import javax.inject.Inject

class ContentValuesFactory @Inject constructor() {

    fun make(vararg values: Pair<String, Any?>) = ContentValues().apply {
        for ((key, value) in values) {
            when (value) {
                is String -> put(key, value)
                is Int -> put(key, value)
                is Long -> put(key, value)
                is Float -> put(key, value)
                is Double -> put(key, value)
                is Byte -> put(key, value)
                is Short -> put(key, value)
                is Boolean -> put(key, value)
                is ByteArray -> put(key, value)
            }
        }
    }

}
