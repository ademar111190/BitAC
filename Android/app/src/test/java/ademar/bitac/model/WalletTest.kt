package ademar.bitac.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class WalletTest {

    @Test
    fun testDataClass() {
        assertThat(Wallet(1L, "name", "address", 2L, 3L, 4L)).isEqualTo(Wallet(1L, "name", "address", 2L, 3L, 4L))
    }

}
