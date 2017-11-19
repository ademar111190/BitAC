package ademar.bitac.interactor

import javax.inject.Inject

class BitcoinUri @Inject constructor() {

    private val regex = "[123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz]".toRegex()

    fun getAddress(action: String?): String? {
        val trimmedAction = action?.trim()
        return when {
            trimmedAction == null -> null
            trimmedAction.startsWith("bitcoin:") -> {
                val candidate = trimmedAction.split("?")[0].replace("bitcoin:", "")
                if (isAnAddress(candidate)) candidate else null
            }
            trimmedAction.isNotEmpty() -> {
                val candidate = trimmedAction.split("?")[0]
                if (isAnAddress(candidate)) candidate else null
            }
            else -> null
        }
    }

    fun getLabel(action: String?): String? {
        if (action != null && action.startsWith("bitcoin:")) {
            val split = action.trim().split("label=")
            if (split.size > 1) {
                return split[1].trim().split("&")[0]
            }
        }
        return null
    }

    // TODO improve to verify if an address is valid even if has valid characters and size
    private fun isAnAddress(candidate: String): Boolean {
        val validSize = candidate.length in 26..35
        val validCharacters = candidate.replace(regex, "").isEmpty()
        return validSize && validCharacters
    }

}
