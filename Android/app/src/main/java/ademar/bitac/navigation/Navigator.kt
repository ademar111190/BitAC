package ademar.bitac.navigation

import ademar.bitac.R
import ademar.bitac.interactor.GetTheme
import ademar.bitac.view.AboutActivity
import ademar.bitac.view.CheckAddressActivity
import ademar.bitac.view.HomeActivity
import ademar.bitac.view.Theme
import android.app.Activity
import javax.inject.Inject

class Navigator @Inject constructor(

        private val activity: Activity,
        private val intentFactory: IntentFactory,
        private val getTheme: GetTheme

) {

    fun launchAbout() {
        activity.startActivity(getAboutIntentByTheme(getTheme.execute()))
    }

    fun launchCheckAddress() {
        activity.startActivity(getCheckAddressIntentByTheme(getTheme.execute()))
    }

    fun launchHome() {
        activity.startActivity(getHomeIntentByTheme(getTheme.execute()))
    }

    fun launchHomeWithNewTheme(theme: Theme) {
        activity.startActivity(getHomeIntentByTheme(theme))
        activity.overridePendingTransition(R.anim.change_theme_in, R.anim.change_theme_out)
        activity.finish()
    }

    private fun getAboutIntentByTheme(theme: Theme) = intentFactory.makeIntent().apply {
        setClass(activity, when (theme) {
            Theme.LIGHT -> AboutActivity.AboutActivityLight::class.java
            Theme.DARK -> AboutActivity.AboutActivityDark::class.java
            Theme.DOGE -> AboutActivity.AboutActivityDoge::class.java
            Theme.ELEVEN -> AboutActivity.AboutActivityEleven::class.java
            Theme.ADA -> AboutActivity.AboutActivityAda::class.java
            else -> AboutActivity.AboutActivityEleven::class.java
        })
    }

    private fun getCheckAddressIntentByTheme(theme: Theme) = intentFactory.makeIntent().apply {
        setClass(activity, when (theme) {
            Theme.LIGHT -> CheckAddressActivity.CheckAddressActivityLight::class.java
            Theme.DARK -> CheckAddressActivity.CheckAddressActivityDark::class.java
            Theme.DOGE -> CheckAddressActivity.CheckAddressActivityDoge::class.java
            Theme.ELEVEN -> CheckAddressActivity.CheckAddressActivityEleven::class.java
            Theme.ADA -> CheckAddressActivity.CheckAddressActivityAda::class.java
            else -> CheckAddressActivity.CheckAddressActivityEleven::class.java
        })
    }

    private fun getHomeIntentByTheme(theme: Theme) = intentFactory.makeIntent().apply {
        setClass(activity, when (theme) {
            Theme.LIGHT -> HomeActivity.HomeActivityLight::class.java
            Theme.DARK -> HomeActivity.HomeActivityDark::class.java
            Theme.DOGE -> HomeActivity.HomeActivityDoge::class.java
            Theme.ELEVEN -> HomeActivity.HomeActivityEleven::class.java
            Theme.ADA -> HomeActivity.HomeActivityAda::class.java
            else -> HomeActivity.HomeActivityEleven::class.java
        })
    }

}
