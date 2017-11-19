package ademar.bitac.model

import ademar.bitac.test.JsonTestUtils
import ademar.bitac.test.fixture.MultiAddressFixture
import com.bluelinelabs.logansquare.LoganSquare
import org.assertj.core.api.Assertions
import org.junit.Test

class MultiAddressTest {

    @Test
    fun testParse() {
        val json = JsonTestUtils.readJson("multi_address")
        val multiAddress = LoganSquare.parse(json, MultiAddress::class.java)
        Assertions.assertThat(multiAddress.addresses).isNotNull
        Assertions.assertThat(multiAddress.addresses?.size).isEqualTo(1)
    }

    @Test
    fun testSerialize() {
        val json = LoganSquare.serialize(MultiAddressFixture.makeModel(10))
        JsonTestUtils.assertJsonListNotNull(json, "addresses")
    }

    @Test
    fun testSerializeListNotEmpty() {
        val json = LoganSquare.serialize(MultiAddressFixture.makeModel(1))
        JsonTestUtils.assertJsonListNotNull(json, "addresses")
    }

    @Test
    fun testSerializeListEmpty() {
        val json = LoganSquare.serialize(MultiAddressFixture.makeModel(0))
        JsonTestUtils.assertJsonListEmpty(json, "addresses")
    }

    @Test
    fun testSerializeListNull() {
        val json = LoganSquare.serialize(MultiAddressFixture.makeModel(-1))
        JsonTestUtils.assertJsonHasNoKey(json, "addresses")
    }

}
