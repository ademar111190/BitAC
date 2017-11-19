package ademar.bitac.interactor

import ademar.bitac.view.Theme
import android.content.Context
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetTheme @Inject constructor(
        private val context: Context
) {

    private val preference by lazy {
        context.getSharedPreferences("theme", Context.MODE_PRIVATE)
    }

    fun execute() = Theme.getTheme(preference.getString("theme", Theme.DEFAULT.tag))

}
