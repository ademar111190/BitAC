package ademar.bitac.view

import ademar.bitac.R
import ademar.bitac.ext.getTheme
import android.os.Bundle
import android.text.Html
import android.text.Html.fromHtml
import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(intent.getTheme().resTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        findViewById<Toolbar>(R.id.toolbar).setNavigationOnClickListener { finish() }

        val aboutText = getString(R.string.about_text)
        val text = findViewById<TextView>(R.id.text)
        text.text = fromHtml(aboutText, Html.FROM_HTML_MODE_LEGACY)
        text.movementMethod = LinkMovementMethod.getInstance()
    }

}
