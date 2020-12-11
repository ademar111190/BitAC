package ademar.bitac.navigation

import ademar.bitac.ext.EXTRA_THEME
import ademar.bitac.interactor.GetTheme
import ademar.bitac.view.*
import android.app.Activity
import javax.inject.Inject

class Navigator @Inject constructor(

        private val activity: Activity,
        private val intentFactory: IntentFactory,
        private val getTheme: GetTheme

) {

    fun launchAbout() = activity.startActivity(intentFactory.makeIntent().apply {
        setClass(activity, AboutActivity::class.java)
        putExtra(EXTRA_THEME, getTheme.execute())
    })

    fun launchCheckAddress() = activity.startActivity(intentFactory.makeIntent().apply {
        setClass(activity, CheckAddressActivity::class.java)
        putExtra(EXTRA_THEME, getTheme.execute())
    })

    fun launchHome(
            finish: Boolean = false
    ) = activity.startActivity(intentFactory.makeIntent().apply {
        setClass(activity, HomeActivity::class.java)
        putExtra(EXTRA_THEME, getTheme.execute())
        if (finish) activity.finishAffinity()
    })

    fun launchHome(theme: Theme) = activity.startActivity(intentFactory.makeIntent().apply {
        setClass(activity, HomeActivity::class.java)
        putExtra(EXTRA_THEME, theme)
        activity.finishAffinity()
    })

    fun launchSettings() = activity.startActivity(intentFactory.makeIntent().apply {
        setClass(activity, SettingsActivity::class.java)
    })

}
