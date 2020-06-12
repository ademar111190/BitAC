package ademar.bitac.presenter

import ademar.bitac.navigation.Navigator
import ademar.bitac.view.Theme
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class SettingsPresenterTest {

    @Mock private lateinit var mockNavigator: Navigator

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testAttach() {
        val view = object : StubSettingsView() {
        }

        val presenter = SettingsPresenter(mockNavigator)
        presenter.view = view
    }

    @Test
    fun testDetach() {
        val presenter = SettingsPresenter(mockNavigator)
        presenter.view = null
    }

    @Test
    fun testAbout() {
        val view = object : StubSettingsView() {
        }

        val presenter = SettingsPresenter(mockNavigator)
        presenter.view = view
        presenter.about()

        verify(mockNavigator).launchAbout()
    }

    @Test
    fun testChangeTheme() {
        val view = object : StubSettingsView() {
        }

        val presenter = SettingsPresenter(mockNavigator)
        presenter.view = view
        presenter.changeTheme(Theme.ELEVEN.tag)

        verify(mockNavigator).launchHome(Theme.ELEVEN)
    }

}
