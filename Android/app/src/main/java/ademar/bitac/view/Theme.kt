package ademar.bitac.view

enum class Theme constructor(

        val tag: String

) {

    DEFAULT("default"),
    LIGHT("light"),
    DARK("dark"),
    DOGE("doge"),
    ELEVEN("eleven"),
    ADA("ada");

    companion object {
        fun getTheme(tag: String) = Theme.values().firstOrNull { it.tag == tag } ?: Theme.DEFAULT
    }

}
