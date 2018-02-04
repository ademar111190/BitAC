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
    }

    @Test
    fun testExecuteDefault() {
        whenever(mockSharedPreferences.getString("theme", Theme.ELEVEN.tag)).thenReturn(Theme.ELEVEN.tag)
        val getTheme = GetTheme(mockContext)
        getTheme.preference = mockSharedPreferences
        val theme = getTheme.execute()
        assertThat(theme).isEqualTo(Theme.ELEVEN)
    }

    @Test
    fun testExecuteLight() {
        whenever(mockSharedPreferences.getString("theme", Theme.ELEVEN.tag)).thenReturn(Theme.LIGHT.tag)
        val getTheme = GetTheme(mockContext)
        getTheme.preference = mockSharedPreferences
        val theme = getTheme.execute()
        assertThat(theme).isEqualTo(Theme.LIGHT)
    }

    @Test
    fun testExecuteDark() {
        whenever(mockSharedPreferences.getString("theme", Theme.ELEVEN.tag)).thenReturn(Theme.DARK.tag)
        val getTheme = GetTheme(mockContext)
        getTheme.preference = mockSharedPreferences
        val theme = getTheme.execute()
        assertThat(theme).isEqualTo(Theme.DARK)
    }

    @Test
    fun testExecuteDoge() {
        whenever(mockSharedPreferences.getString("theme", Theme.ELEVEN.tag)).thenReturn(Theme.DOGE.tag)
        val getTheme = GetTheme(mockContext)
        getTheme.preference = mockSharedPreferences
        val theme = getTheme.execute()
        assertThat(theme).isEqualTo(Theme.DOGE)
    }

    @Test
    fun testExecuteEleven() {
        whenever(mockSharedPreferences.getString("theme", Theme.ELEVEN.tag)).thenReturn(Theme.ELEVEN.tag)
        val getTheme = GetTheme(mockContext)
        getTheme.preference = mockSharedPreferences
        val theme = getTheme.execute()
        assertThat(theme).isEqualTo(Theme.ELEVEN)
    }

    @Test
    fun testExecuteAda() {
        whenever(mockSharedPreferences.getString("theme", Theme.ELEVEN.tag)).thenReturn(Theme.ADA.tag)
        val getTheme = GetTheme(mockContext)
        getTheme.preference = mockSharedPreferences
        val theme = getTheme.execute()
        assertThat(theme).isEqualTo(Theme.ADA)
    }

}
