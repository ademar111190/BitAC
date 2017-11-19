package ademar.bitac.interactor

import ademar.bitac.R
import android.content.Context
import com.nhaarman.mockito_kotlin.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class CleanWalletNameTest {

    @Mock private lateinit var mockContext: Context

    private val unnamed = "UNNAMED"

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        whenever(mockContext.getString(R.string.app_unnamed)).thenReturn(unnamed)
    }

    @Test
    fun testExecuteNull() {
        val name = CleanWalletName(mockContext).execute(null)
        assertThat(name).isEqualTo(unnamed)
    }

    @Test
    fun testExecuteEmpty() {
        val name = CleanWalletName(mockContext).execute("")
        assertThat(name).isEqualTo(unnamed)
    }

    @Test
    fun testExecuteEspace() {
        val name = CleanWalletName(mockContext).execute(" ")
        assertThat(name).isEqualTo(unnamed)
    }

    @Test
    fun testExecuteNewLine() {
        val name = CleanWalletName(mockContext).execute("\n")
        assertThat(name).isEqualTo(unnamed)
    }

    @Test
    fun testExecuteValid() {
        val name = CleanWalletName(mockContext).execute("A name")
        assertThat(name).isEqualTo("A name")
    }

    @Test
    fun testExecuteValidRightSpace() {
        val name = CleanWalletName(mockContext).execute("A name ")
        assertThat(name).isEqualTo("A name")
    }

    @Test
    fun testExecuteValidLeftSpace() {
        val name = CleanWalletName(mockContext).execute(" A name")
        assertThat(name).isEqualTo("A name")
    }

    @Test
    fun testExecuteValidTwoWaySpace() {
        val name = CleanWalletName(mockContext).execute(" A name ")
        assertThat(name).isEqualTo("A name")
    }

    @Test
    fun testExecuteValidHardScenery() {
        val name = CleanWalletName(mockContext).execute(" \n A name \n")
        assertThat(name).isEqualTo("A name")
    }

    @Test
    fun testExecuteValidEncoded() {
        val name = CleanWalletName(mockContext).execute("%20A%20name")
        assertThat(name).isEqualTo("A name")
    }

    @Test
    fun testExecuteValidSpecialCharactersEncoded() {
        val name = CleanWalletName(mockContext).execute("%0AAn%20esp%C3%A9%C3%A7i%C3%A0l%20n%C3%A3m%C3%AA")
        assertThat(name).isEqualTo("An espéçiàl nãmê")
    }

}
