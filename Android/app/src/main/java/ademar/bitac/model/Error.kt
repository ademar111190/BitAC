package ademar.bitac.model

import java.util.*

class Error(

        message: String,
        private var code: Int

) : Throwable(message) {

    override fun equals(other: Any?) = other is Error && code == other.code && message == other.message

    override fun hashCode() = Objects.hash(code, message)

}
