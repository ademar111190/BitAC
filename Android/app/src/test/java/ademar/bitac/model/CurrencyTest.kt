package ademar.bitac.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class CurrencyTest {

    @Test
    fun testDataClass() {
        assertThat(Currency("An id", "A name", "A symbol")).isEqualTo(Currency("An id", "A name", "A symbol"))
    }

}
