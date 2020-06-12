package ademar.bitac.presenter

import ademar.bitac.navigation.Navigator
import ademar.bitac.view.Theme
import javax.inject.Inject

class SettingsPresenter @Inject constructor(
        private val navigator: Navigator
) {

    var view: SettingsView? = null

    fun about() {
        navigator.launchAbout()
    }

    fun changeTheme(themeKey: String) {
        val theme = Theme.getTheme(themeKey)
        navigator.launchHome(theme)
    }

}
