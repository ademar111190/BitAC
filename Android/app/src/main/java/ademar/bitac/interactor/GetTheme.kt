package ademar.bitac.interactor

import ademar.bitac.view.Theme
import android.content.Context
import android.preference.PreferenceManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetTheme @Inject constructor(context: Context) {

    var preference = PreferenceManager.getDefaultSharedPreferences(context)

    fun execute() = Theme.getTheme(preference.getString("theme", Theme.ELEVEN.tag))

}
