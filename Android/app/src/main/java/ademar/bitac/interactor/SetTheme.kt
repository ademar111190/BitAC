package ademar.bitac.interactor

import ademar.bitac.view.Theme
import android.content.Context
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SetTheme @Inject constructor(

        private val context: Context

) {

    private val preference by lazy {
        context.getSharedPreferences("theme", Context.MODE_PRIVATE)
    }

    fun execute(theme: Theme) = preference.edit()
            .putString("theme", theme.tag)
            .apply()

}
