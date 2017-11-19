package ademar.bitac.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class FieldPropertyTest {

    @Test(expected = UninitializedPropertyAccessException::class)
    fun testGetValueUninitialized() {
        Stub().delegated
    }

    @Test
    fun testSetGet() {
        val stub = Stub()
        stub.delegated = "Test"
        val delegated = stub.delegated

        System.gc()
        System.runFinalization()

        assertThat(delegated).isEqualTo("Test")
    }

    class Stub {

        var delegated: String by FieldProperty()

    }

}
