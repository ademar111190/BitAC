package ademar.bitac.view

import ademar.bitac.R
import ademar.bitac.injection.Injector
import ademar.bitac.presenter.SettingsPresenter
import ademar.bitac.presenter.SettingsView
import android.os.Bundle
import android.preference.PreferenceFragment
import android.widget.Toast
import javax.inject.Inject

class SettingsFragment : PreferenceFragment(), SettingsView {

    @Inject lateinit var presenter: SettingsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.preferences)
        Injector.get(activity).inject(this)

        presenter.view = this

        findPreference("about").setOnPreferenceClickListener {
            presenter.about()
            true
        }

        findPreference("theme").setOnPreferenceChangeListener { preference, value ->
            when {
                preference.key == "theme" && value is String -> presenter.changeTheme(value)
                else -> null
            } != null
        }
    }

    override fun showError(throwable: Throwable) {
        Toast.makeText(activity, throwable.message, Toast.LENGTH_SHORT).show()
    }

}
