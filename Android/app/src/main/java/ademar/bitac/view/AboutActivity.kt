package ademar.bitac.view

import ademar.bitac.R
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.text.Html.fromHtml
import android.text.method.LinkMovementMethod
import kotlinx.android.synthetic.main.activity_about.*

abstract class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        toolbar.setNavigationOnClickListener { finish() }

        val aboutText = getString(R.string.about_text)
        text.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            fromHtml(aboutText, Html.FROM_HTML_MODE_LEGACY)
        } else {
            @Suppress("DEPRECATION")
            fromHtml(aboutText)
        }
        text.movementMethod = LinkMovementMethod.getInstance()
    }

    class AboutActivityLight : AboutActivity()
    class AboutActivityDark : AboutActivity()
    class AboutActivityDoge : AboutActivity()
    class AboutActivityEleven : AboutActivity()
    class AboutActivityAda : AboutActivity()

}
