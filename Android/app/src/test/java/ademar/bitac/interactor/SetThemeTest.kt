package ademar.bitac.interactor

import ademar.bitac.view.Theme
import android.content.Context
import android.content.SharedPreferences
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class SetThemeTest {

    @Mock private lateinit var mockContext: Context
    @Mock private lateinit var mockSharedPreferences: SharedPreferences
    @Mock private lateinit var mockSharedPreferencesEditor: SharedPreferences.Editor

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        whenever(mockContext.getSharedPreferences("theme", Context.MODE_PRIVATE)).thenReturn(mockSharedPreferences)
        whenever(mockSharedPreferences.edit()).thenReturn(mockSharedPreferencesEditor)
        whenever(mockSharedPreferencesEditor.putString(any(), any())).thenReturn(mockSharedPreferencesEditor)
    }

    @Test
    fun testExecuteDefault() {
        whenever(mockSharedPreferences.getString("theme", Theme.DEFAULT.tag)).thenReturn(Theme.DEFAULT.tag)
        SetTheme(mockContext).execute(Theme.DEFAULT)
        verify(mockSharedPreferencesEditor).putString("theme", Theme.DEFAULT.tag)
        verify(mockSharedPreferencesEditor).apply()
        verifyNoMoreInteractions(mockSharedPreferencesEditor)
    }

    @Test
    fun testExecuteLight() {
        whenever(mockSharedPreferences.getString("theme", Theme.DEFAULT.tag)).thenReturn(Theme.LIGHT.tag)
        SetTheme(mockContext).execute(Theme.LIGHT)
        verify(mockSharedPreferencesEditor).putString("theme", Theme.LIGHT.tag)
        verify(mockSharedPreferencesEditor).apply()
        verifyNoMoreInteractions(mockSharedPreferencesEditor)
    }

    @Test
    fun testExecuteDark() {
        whenever(mockSharedPreferences.getString("theme", Theme.DEFAULT.tag)).thenReturn(Theme.DARK.tag)
        SetTheme(mockContext).execute(Theme.DARK)
        verify(mockSharedPreferencesEditor).putString("theme", Theme.DARK.tag)
        verify(mockSharedPreferencesEditor).apply()
        verifyNoMoreInteractions(mockSharedPreferencesEditor)
    }

    @Test
    fun testExecuteDoge() {
        whenever(mockSharedPreferences.getString("theme", Theme.DEFAULT.tag)).thenReturn(Theme.DOGE.tag)
        SetTheme(mockContext).execute(Theme.DOGE)
        verify(mockSharedPreferencesEditor).putString("theme", Theme.DOGE.tag)
        verify(mockSharedPreferencesEditor).apply()
        verifyNoMoreInteractions(mockSharedPreferencesEditor)
    }

}
