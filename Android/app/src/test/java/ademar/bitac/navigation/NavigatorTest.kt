package ademar.bitac.navigation

import ademar.bitac.interactor.GetTheme
import ademar.bitac.view.*
import android.app.Activity
import android.content.Intent
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class NavigatorTest {

    @Mock private lateinit var mockActivity: Activity
    @Mock private lateinit var mockIntentFactory: IntentFactory
    @Mock private lateinit var mockIntent: Intent
    @Mock private lateinit var mockGetTheme: GetTheme
    @Mock private lateinit var mockTheme: Theme

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        whenever(mockIntentFactory.makeIntent()).thenReturn(mockIntent)
    }

    @Test
    fun testLaunchAboutDefault() {
        whenever(mockGetTheme.execute()).thenReturn(Theme.ELEVEN)
        Navigator(mockActivity, mockIntentFactory, mockGetTheme).launchAbout()
        verify(mockIntent).setClass(mockActivity, AboutActivity::class.java)
        verify(mockIntent).putExtra(EXTRA_THEME, Theme.ELEVEN)
        verify(mockActivity).startActivity(mockIntent)
        verifyNoMoreInteractions(mockActivity, mockIntent)
    }

    @Test
    fun testLaunchAboutLight() {
        whenever(mockGetTheme.execute()).thenReturn(Theme.LIGHT)
        Navigator(mockActivity, mockIntentFactory, mockGetTheme).launchAbout()
        verify(mockIntent).setClass(mockActivity, AboutActivity::class.java)
        verify(mockIntent).putExtra(EXTRA_THEME, Theme.LIGHT)
        verify(mockActivity).startActivity(mockIntent)
        verifyNoMoreInteractions(mockActivity, mockIntent)
    }

    @Test
    fun testLaunchAboutDark() {
        whenever(mockGetTheme.execute()).thenReturn(Theme.DARK)
        Navigator(mockActivity, mockIntentFactory, mockGetTheme).launchAbout()
        verify(mockIntent).setClass(mockActivity, AboutActivity::class.java)
        verify(mockIntent).putExtra(EXTRA_THEME, Theme.DARK)
        verify(mockActivity).startActivity(mockIntent)
        verifyNoMoreInteractions(mockActivity, mockIntent)
    }

    @Test
    fun testLaunchAboutDoge() {
        whenever(mockGetTheme.execute()).thenReturn(Theme.DOGE)
        Navigator(mockActivity, mockIntentFactory, mockGetTheme).launchAbout()
        verify(mockIntent).setClass(mockActivity, AboutActivity::class.java)
        verify(mockIntent).putExtra(EXTRA_THEME, Theme.DOGE)
        verify(mockActivity).startActivity(mockIntent)
        verifyNoMoreInteractions(mockActivity, mockIntent)
    }

    @Test
    fun testLaunchCheckAddressDefault() {
        whenever(mockGetTheme.execute()).thenReturn(Theme.ELEVEN)
        Navigator(mockActivity, mockIntentFactory, mockGetTheme).launchCheckAddress()
        verify(mockIntent).setClass(mockActivity, CheckAddressActivity::class.java)
        verify(mockIntent).putExtra(EXTRA_THEME, Theme.ELEVEN)
        verify(mockActivity).startActivity(mockIntent)
        verifyNoMoreInteractions(mockActivity, mockIntent)
    }

    @Test
    fun testLaunchCheckAddressLight() {
        whenever(mockGetTheme.execute()).thenReturn(Theme.LIGHT)
        Navigator(mockActivity, mockIntentFactory, mockGetTheme).launchCheckAddress()
        verify(mockIntent).setClass(mockActivity, CheckAddressActivity::class.java)
        verify(mockIntent).putExtra(EXTRA_THEME, Theme.LIGHT)
        verify(mockActivity).startActivity(mockIntent)
        verifyNoMoreInteractions(mockActivity, mockIntent)
    }

    @Test
    fun testLaunchCheckAddressDark() {
        whenever(mockGetTheme.execute()).thenReturn(Theme.DARK)
        Navigator(mockActivity, mockIntentFactory, mockGetTheme).launchCheckAddress()
        verify(mockIntent).setClass(mockActivity, CheckAddressActivity::class.java)
        verify(mockIntent).putExtra(EXTRA_THEME, Theme.DARK)
        verify(mockActivity).startActivity(mockIntent)
        verifyNoMoreInteractions(mockActivity, mockIntent)
    }

    @Test
    fun testLaunchCheckAddressDoge() {
        whenever(mockGetTheme.execute()).thenReturn(Theme.DOGE)
        Navigator(mockActivity, mockIntentFactory, mockGetTheme).launchCheckAddress()
        verify(mockIntent).setClass(mockActivity, CheckAddressActivity::class.java)
        verify(mockIntent).putExtra(EXTRA_THEME, Theme.DOGE)
        verify(mockActivity).startActivity(mockIntent)
        verifyNoMoreInteractions(mockActivity, mockIntent)
    }

    @Test
    fun testLaunchHomeDefault() {
        whenever(mockGetTheme.execute()).thenReturn(Theme.ELEVEN)
        Navigator(mockActivity, mockIntentFactory, mockGetTheme).launchHome()
        verify(mockIntent).setClass(mockActivity, HomeActivity::class.java)
        verify(mockIntent).putExtra(EXTRA_THEME, Theme.ELEVEN)
        verify(mockActivity).startActivity(mockIntent)
        verifyNoMoreInteractions(mockActivity, mockIntent)
    }

    @Test
    fun testLaunchHomeLight() {
        whenever(mockGetTheme.execute()).thenReturn(Theme.LIGHT)
        Navigator(mockActivity, mockIntentFactory, mockGetTheme).launchHome()
        verify(mockIntent).setClass(mockActivity, HomeActivity::class.java)
        verify(mockIntent).putExtra(EXTRA_THEME, Theme.LIGHT)
        verify(mockActivity).startActivity(mockIntent)
        verifyNoMoreInteractions(mockActivity, mockIntent)
    }

    @Test
    fun testLaunchHomeDark() {
        whenever(mockGetTheme.execute()).thenReturn(Theme.DARK)
        Navigator(mockActivity, mockIntentFactory, mockGetTheme).launchHome()
        verify(mockIntent).setClass(mockActivity, HomeActivity::class.java)
        verify(mockIntent).putExtra(EXTRA_THEME, Theme.DARK)
        verify(mockActivity).startActivity(mockIntent)
        verifyNoMoreInteractions(mockActivity, mockIntent)
    }

    @Test
    fun testLaunchHomeDoge() {
        whenever(mockGetTheme.execute()).thenReturn(Theme.DOGE)
        Navigator(mockActivity, mockIntentFactory, mockGetTheme).launchHome()
        verify(mockIntent).setClass(mockActivity, HomeActivity::class.java)
        verify(mockIntent).putExtra(EXTRA_THEME, Theme.DOGE)
        verify(mockActivity).startActivity(mockIntent)
        verifyNoMoreInteractions(mockActivity, mockIntent)
    }

    @Test
    fun testLaunchHomeWithTheme() {
        Navigator(mockActivity, mockIntentFactory, mockGetTheme).launchHome(mockTheme)
        verify(mockIntent).setClass(mockActivity, HomeActivity::class.java)
        verify(mockActivity).startActivity(mockIntent)
        verify(mockIntent).putExtra(EXTRA_THEME, mockTheme)
        verify(mockActivity).finishAffinity()
        verifyNoMoreInteractions(mockActivity, mockIntent)
    }

    @Test
    fun testLaunchSettings() {
        Navigator(mockActivity, mockIntentFactory, mockGetTheme).launchSettings()
        verify(mockIntent).setClass(mockActivity, SettingsActivity::class.java)
        verify(mockActivity).startActivity(mockIntent)
        verifyNoMoreInteractions(mockActivity, mockIntent)
    }

}
