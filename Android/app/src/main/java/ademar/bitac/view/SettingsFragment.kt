package ademar.bitac.view

import ademar.bitac.R
import ademar.bitac.injection.Injector
import ademar.bitac.interactor.Analytics
import ademar.bitac.navigation.Navigator
import android.os.Bundle
import android.preference.PreferenceFragment
import javax.inject.Inject

class SettingsFragment : PreferenceFragment() {

    @Inject lateinit var navigator: Navigator
    @Inject lateinit var analytics: Analytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.preferences)
        Injector.get(activity).inject(this)

        findPreference("about").setOnPreferenceClickListener {
            analytics.trackAbout()
            navigator.launchAbout()
            true
        }

        findPreference("theme").setOnPreferenceChangeListener { preference, value ->
            when {
                preference.key == "theme" && value is String -> {
                    val theme = Theme.getTheme(value)
                    analytics.trackThemeChange(theme)
                    navigator.launchHome(theme)
                }
                else -> null
            } != null
        }
    }

}
