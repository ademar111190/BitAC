package ademar.bitac.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ProviderTest {

    @Test
    fun testDataClass() {
        assertThat(Provider("An id", "A name")).isEqualTo(Provider("An id", "A name"))
    }

}
