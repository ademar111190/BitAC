package ademar.bitac.model

import ademar.bitac.test.JsonTestUtils
import ademar.bitac.test.fixture.AddressFixture
import com.bluelinelabs.logansquare.LoganSquare
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class AddressTest {

    @Test
    fun testParse() {
        val json = JsonTestUtils.readJson("address")
        val address = LoganSquare.parse(json, Address::class.java)
        assertThat(address.address).isEqualTo(AddressFixture.address)
        assertThat(address.balance).isEqualTo(AddressFixture.balance)
    }

    @Test
    fun testSerialize() {
        val json = LoganSquare.serialize(AddressFixture.makeModel())
        JsonTestUtils.assertJsonStringValue(json, "address", AddressFixture.address)
        JsonTestUtils.assertJsonLongValue(json, "final_balance", AddressFixture.balance)
    }

}
