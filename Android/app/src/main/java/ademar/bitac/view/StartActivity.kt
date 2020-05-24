package ademar.bitac.view

import ademar.bitac.injection.Injector
import ademar.bitac.navigation.Navigator
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import javax.inject.Inject

class StartActivity : AppCompatActivity() {

    @Inject lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injector.get(this).inject(this)
        navigator.launchHome()
        finish()
    }

}
