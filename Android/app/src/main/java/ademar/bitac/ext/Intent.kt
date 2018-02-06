package ademar.bitac.ext

import ademar.bitac.view.Theme
import android.content.Intent

fun Intent.getTheme() = getSerializableExtra(EXTRA_THEME) as Theme
