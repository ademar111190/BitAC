package ademar.bitac.interactor

import ademar.bitac.view.Theme
import android.content.Context
import android.content.SharedPreferences
import com.nhaarman.mockito_kotlin.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class GetThemeTest {

    @Mock private lateinit var mockContext: Context
    @Mock private lateinit var mockSharedPreferences: SharedPreferences

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        whenever(mockContext.getSharedPreferences("theme", Context.MODE_PRIVATE)).thenReturn(mockSharedPreferences)
    }

    @Test
    fun testExecuteDefault() {
        whenever(mockSharedPreferences.getString("theme", Theme.DEFAULT.tag)).thenReturn(Theme.DEFAULT.tag)
        val theme = GetTheme(mockContext).execute()
        assertThat(theme).isEqualTo(Theme.DEFAULT)
    }

    @Test
    fun testExecuteLight() {
        whenever(mockSharedPreferences.getString("theme", Theme.DEFAULT.tag)).thenReturn(Theme.LIGHT.tag)
        val theme = GetTheme(mockContext).execute()
        assertThat(theme).isEqualTo(Theme.LIGHT)
    }

    @Test
    fun testExecuteDark() {
        whenever(mockSharedPreferences.getString("theme", Theme.DEFAULT.tag)).thenReturn(Theme.DARK.tag)
        val theme = GetTheme(mockContext).execute()
        assertThat(theme).isEqualTo(Theme.DARK)
    }

    @Test
    fun testExecuteDoge() {
        whenever(mockSharedPreferences.getString("theme", Theme.DEFAULT.tag)).thenReturn(Theme.DOGE.tag)
        val theme = GetTheme(mockContext).execute()
        assertThat(theme).isEqualTo(Theme.DOGE)
    }

    @Test
    fun testExecuteEleven() {
        whenever(mockSharedPreferences.getString("theme", Theme.DEFAULT.tag)).thenReturn(Theme.ELEVEN.tag)
        val theme = GetTheme(mockContext).execute()
        assertThat(theme).isEqualTo(Theme.ELEVEN)
    }

    @Test
    fun testExecuteAda() {
        whenever(mockSharedPreferences.getString("theme", Theme.DEFAULT.tag)).thenReturn(Theme.ADA.tag)
        val theme = GetTheme(mockContext).execute()
        assertThat(theme).isEqualTo(Theme.ADA)
    }

}
