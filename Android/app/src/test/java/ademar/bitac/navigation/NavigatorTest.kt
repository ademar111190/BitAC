package ademar.bitac.navigation

import ademar.bitac.R
import ademar.bitac.interactor.GetTheme
import ademar.bitac.view.AboutActivity
import ademar.bitac.view.CheckAddressActivity
import ademar.bitac.view.HomeActivity
import ademar.bitac.view.Theme
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

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        whenever(mockIntentFactory.makeIntent()).thenReturn(mockIntent)
    }

    @Test
    fun testLaunchAboutDefault() {
        whenever(mockGetTheme.execute()).thenReturn(Theme.LIGHT)
        Navigator(mockActivity, mockIntentFactory, mockGetTheme).launchAbout()
        verify(mockIntent).setClass(mockActivity, AboutActivity.AboutActivityLight::class.java)
        verify(mockActivity).startActivity(mockIntent)
        verifyNoMoreInteractions(mockActivity, mockIntent)
    }

    @Test
    fun testLaunchAboutLight() {
        whenever(mockGetTheme.execute()).thenReturn(Theme.LIGHT)
        Navigator(mockActivity, mockIntentFactory, mockGetTheme).launchAbout()
        verify(mockIntent).setClass(mockActivity, AboutActivity.AboutActivityLight::class.java)
        verify(mockActivity).startActivity(mockIntent)
        verifyNoMoreInteractions(mockActivity, mockIntent)
    }

    @Test
    fun testLaunchAboutDark() {
        whenever(mockGetTheme.execute()).thenReturn(Theme.DARK)
        Navigator(mockActivity, mockIntentFactory, mockGetTheme).launchAbout()
        verify(mockIntent).setClass(mockActivity, AboutActivity.AboutActivityDark::class.java)
        verify(mockActivity).startActivity(mockIntent)
        verifyNoMoreInteractions(mockActivity, mockIntent)
    }

    @Test
    fun testLaunchAboutDoge() {
        whenever(mockGetTheme.execute()).thenReturn(Theme.DOGE)
        Navigator(mockActivity, mockIntentFactory, mockGetTheme).launchAbout()
        verify(mockIntent).setClass(mockActivity, AboutActivity.AboutActivityDoge::class.java)
        verify(mockActivity).startActivity(mockIntent)
        verifyNoMoreInteractions(mockActivity, mockIntent)
    }

    @Test
    fun testLaunchCheckAddressDefault() {
        whenever(mockGetTheme.execute()).thenReturn(Theme.LIGHT)
        Navigator(mockActivity, mockIntentFactory, mockGetTheme).launchCheckAddress()
        verify(mockIntent).setClass(mockActivity, CheckAddressActivity.CheckAddressActivityLight::class.java)
        verify(mockActivity).startActivity(mockIntent)
        verifyNoMoreInteractions(mockActivity, mockIntent)
    }

    @Test
    fun testLaunchCheckAddressLight() {
        whenever(mockGetTheme.execute()).thenReturn(Theme.LIGHT)
        Navigator(mockActivity, mockIntentFactory, mockGetTheme).launchCheckAddress()
        verify(mockIntent).setClass(mockActivity, CheckAddressActivity.CheckAddressActivityLight::class.java)
        verify(mockActivity).startActivity(mockIntent)
        verifyNoMoreInteractions(mockActivity, mockIntent)
    }

    @Test
    fun testLaunchCheckAddressDark() {
        whenever(mockGetTheme.execute()).thenReturn(Theme.DARK)
        Navigator(mockActivity, mockIntentFactory, mockGetTheme).launchCheckAddress()
        verify(mockIntent).setClass(mockActivity, CheckAddressActivity.CheckAddressActivityDark::class.java)
        verify(mockActivity).startActivity(mockIntent)
        verifyNoMoreInteractions(mockActivity, mockIntent)
    }

    @Test
    fun testLaunchCheckAddressDoge() {
        whenever(mockGetTheme.execute()).thenReturn(Theme.DOGE)
        Navigator(mockActivity, mockIntentFactory, mockGetTheme).launchCheckAddress()
        verify(mockIntent).setClass(mockActivity, CheckAddressActivity.CheckAddressActivityDoge::class.java)
        verify(mockActivity).startActivity(mockIntent)
        verifyNoMoreInteractions(mockActivity, mockIntent)
    }

    @Test
    fun testLaunchHomeDefault() {
        whenever(mockGetTheme.execute()).thenReturn(Theme.LIGHT)
        Navigator(mockActivity, mockIntentFactory, mockGetTheme).launchHome()
        verify(mockIntent).setClass(mockActivity, HomeActivity.HomeActivityLight::class.java)
        verify(mockActivity).startActivity(mockIntent)
        verifyNoMoreInteractions(mockActivity, mockIntent)
    }

    @Test
    fun testLaunchHomeLight() {
        whenever(mockGetTheme.execute()).thenReturn(Theme.LIGHT)
        Navigator(mockActivity, mockIntentFactory, mockGetTheme).launchHome()
        verify(mockIntent).setClass(mockActivity, HomeActivity.HomeActivityLight::class.java)
        verify(mockActivity).startActivity(mockIntent)
        verifyNoMoreInteractions(mockActivity, mockIntent)
    }

    @Test
    fun testLaunchHomeDark() {
        whenever(mockGetTheme.execute()).thenReturn(Theme.DARK)
        Navigator(mockActivity, mockIntentFactory, mockGetTheme).launchHome()
        verify(mockIntent).setClass(mockActivity, HomeActivity.HomeActivityDark::class.java)
        verify(mockActivity).startActivity(mockIntent)
        verifyNoMoreInteractions(mockActivity, mockIntent)
    }

    @Test
    fun testLaunchHomeDoge() {
        whenever(mockGetTheme.execute()).thenReturn(Theme.DOGE)
        Navigator(mockActivity, mockIntentFactory, mockGetTheme).launchHome()
        verify(mockIntent).setClass(mockActivity, HomeActivity.HomeActivityDoge::class.java)
        verify(mockActivity).startActivity(mockIntent)
        verifyNoMoreInteractions(mockActivity, mockIntent)
    }

    @Test
    fun testLaunchHomeWithNewTheme() {
        whenever(mockGetTheme.execute()).thenReturn(Theme.LIGHT)
        Navigator(mockActivity, mockIntentFactory, mockGetTheme).launchHomeWithNewTheme(Theme.DARK)
        verify(mockIntent).setClass(mockActivity, HomeActivity.HomeActivityDark::class.java)
        verify(mockActivity).startActivity(mockIntent)
        verify(mockActivity).overridePendingTransition(R.anim.change_theme_in, R.anim.change_theme_out)
        verify(mockActivity).finish()
        verifyNoMoreInteractions(mockActivity, mockIntent)
    }

}
