package ademar.bitac.view

import ademar.bitac.R

enum class Theme constructor(

        val tag: String,
        val resTheme: Int,
        val resDialogTheme: Int

) {

    LIGHT("light", R.style.AppLightTheme, R.style.AppDialogLightTheme),
    DARK("dark", R.style.AppDarkTheme, R.style.AppDialogDarkTheme),
    DOGE("doge", R.style.AppDogeTheme, R.style.AppDialogDogeTheme),
    ELEVEN("eleven", R.style.AppElevenTheme, R.style.AppDialogElevenTheme),
    ADA("ada", R.style.AppAdaTheme, R.style.AppDialogAdaTheme);

    companion object {
        fun getTheme(tag: String) = Theme.values().firstOrNull { it.tag == tag } ?: Theme.ELEVEN
    }

}
