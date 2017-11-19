package ademar.bitac.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ErrorTest {

    @Test
    fun testEquality() {
        assertThat(Error("message", 1)).isEqualTo(Error("message", 1))
        assertThat(Error("message", 1)).isNotEqualTo(Error("message2", 1))
        assertThat(Error("message", 1)).isNotEqualTo(Error("message", 2))
    }

}
