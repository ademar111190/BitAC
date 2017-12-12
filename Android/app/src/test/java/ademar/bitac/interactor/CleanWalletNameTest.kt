package ademar.bitac.interactor

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class CleanWalletNameTest {

    private val default = "DEFAULT"

    @Test
    fun testExecuteNull() {
        val name = CleanWalletName().execute(null, default)
        assertThat(name).isEqualTo(default)
    }

    @Test
    fun testExecuteEmpty() {
        val name = CleanWalletName().execute("", default)
        assertThat(name).isEqualTo(default)
    }

    @Test
    fun testExecuteSpace() {
        val name = CleanWalletName().execute(" ", default)
        assertThat(name).isEqualTo(default)
    }

    @Test
    fun testExecuteNewLine() {
        val name = CleanWalletName().execute("\n", default)
        assertThat(name).isEqualTo(default)
    }

    @Test
    fun testExecuteEncoded() {
        val name = CleanWalletName().execute("%20", default)
        assertThat(name).isEqualTo(default)
    }

    @Test
    fun testExecuteValid() {
        val name = CleanWalletName().execute("A name", default)
        assertThat(name).isEqualTo("A name")
    }

    @Test
    fun testExecuteValidRightSpace() {
        val name = CleanWalletName().execute("A name ", default)
        assertThat(name).isEqualTo("A name")
    }

    @Test
    fun testExecuteValidLeftSpace() {
        val name = CleanWalletName().execute(" A name", default)
        assertThat(name).isEqualTo("A name")
    }

    @Test
    fun testExecuteValidTwoWaySpace() {
        val name = CleanWalletName().execute(" A name ", default)
        assertThat(name).isEqualTo("A name")
    }

    @Test
    fun testExecuteValidHardScenery() {
        val name = CleanWalletName().execute(" \n A name \n", default)
        assertThat(name).isEqualTo("A name")
    }

    @Test
    fun testExecuteValidEncoded() {
        val name = CleanWalletName().execute("%20A%20name", default)
        assertThat(name).isEqualTo("A name")
    }

    @Test
    fun testExecuteValidSpecialCharactersEncoded() {
        val name = CleanWalletName().execute("%0AAn%20esp%C3%A9%C3%A7i%C3%A0l%20n%C3%A3m%C3%AA", default)
        assertThat(name).isEqualTo("An espéçiàl nãmê")
    }

}
