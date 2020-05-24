package ademar.bitac.test

import org.assertj.core.api.Assertions.assertThat
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.stream.Collectors

object JsonTestUtils {

    fun readJson(fileName: String): String {
        val stream = javaClass.classLoader?.getResourceAsStream("json/$fileName.json")
                ?: throw IllegalStateException("Invalid Classloader")
        return BufferedReader(InputStreamReader(stream)).lines().collect(Collectors.joining("\n"))
    }

    fun assertJsonHasNoKey(json: String, key: String) {
        assertThat(json).doesNotContain("\"$key\":")
    }

    fun assertJsonListEmpty(json: String, key: String) {
        assertThat(json).contains("\"$key\":[]")
    }

    fun assertJsonListNotNull(json: String, key: String) {
        assertThat(json).contains("\"$key\":[")
    }

    fun assertJsonLongValue(json: String, key: String, value: Long) {
        assertThat(json).contains("\"$key\":$value")
    }

    fun assertJsonStringValue(json: String, key: String, value: String) {
        assertThat(json).contains("\"$key\":\"$value\"")
    }

}
