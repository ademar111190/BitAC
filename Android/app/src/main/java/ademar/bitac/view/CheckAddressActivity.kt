package ademar.bitac.view

import ademar.bitac.R
import ademar.bitac.ext.addOnAnimationEndListener
import ademar.bitac.ext.getTheme
import ademar.bitac.ext.setActionNext
import ademar.bitac.ext.setActionSend
import ademar.bitac.injection.Injector
import ademar.bitac.presenter.CheckAddressPresenter
import ademar.bitac.presenter.CheckAddressView
import ademar.bitac.viewmodel.WalletViewModel
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.zxing.integration.android.IntentIntegrator
import javax.inject.Inject

class CheckAddressActivity : AppCompatActivity(), CheckAddressView {

    @Inject lateinit var presenter: CheckAddressPresenter

    private var status = ScreenStatus.INITIAL

    private val qrReader by lazy {
        IntentIntegrator(this)
                .setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
                .setPrompt(getString(R.string.check_address_qr_hint))
                .setBeepEnabled(true)
                .setOrientationLocked(false)
                .setBarcodeImageEnabled(false)
    }

    private val animDuration by lazy {
        resources.getInteger(android.R.integer.config_shortAnimTime).toLong()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(intent.getTheme().resDialogTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_address)

        Injector.get(this).inject(this)

        findViewById<Toolbar>(R.id.toolbar).setNavigationOnClickListener { finish() }
        findViewById<View>(R.id.input_qr_code).setOnClickListener { qrReader.initiateScan() }

        presenter.view = this
        presenter.loadData()

        findViewById<View>(R.id.input_cancel).setOnClickListener {
            presenter.cancel()
        }
        findViewById<View>(R.id.input_check).setOnClickListener {
            presenter.check(findViewById<EditText>(R.id.input_address).text.toString())
        }
        findViewById<View>(R.id.output_change).setOnClickListener {
            presenter.change()
        }
        findViewById<View>(R.id.output_save).setOnClickListener {
            presenter.save(findViewById<EditText>(R.id.output_name).text.toString())
        }
        findViewById<EditText>(R.id.input_address).setActionNext {
            presenter.check(findViewById<EditText>(R.id.input_address).text.toString())
        }
        findViewById<EditText>(R.id.output_name).setActionSend {
            presenter.save(findViewById<EditText>(R.id.output_name).text.toString())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.view = null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        presenter.parseAction(IntentIntegrator.parseActivityResult(requestCode, resultCode, data)?.contents)
    }

    override fun showInput(viewModel: WalletViewModel) {
        findViewById<View>(R.id.root).post {
            val width = findViewById<View>(R.id.root).width.toFloat()

            findViewById<EditText>(R.id.input_address).setText(viewModel.address)
            findViewById<TextView>(R.id.output_address).text = viewModel.address
            findViewById<TextView>(R.id.output_balance).text = viewModel.balance
            findViewById<EditText>(R.id.output_name).setText(viewModel.name)

            findViewById<View>(R.id.input_root).visibility = View.VISIBLE
            findViewById<View>(R.id.output_root).visibility = View.VISIBLE

            findViewById<View>(R.id.input_cancel).visibility = View.VISIBLE
            findViewById<View>(R.id.input_check).visibility = View.VISIBLE
            findViewById<View>(R.id.input_load).visibility = View.GONE

            if (status == ScreenStatus.SAVE) {
                val animatorSet = AnimatorSet()
                animatorSet.duration = animDuration
                animatorSet.interpolator = AccelerateDecelerateInterpolator()
                animatorSet.addOnAnimationEndListener {
                    findViewById<View>(R.id.output_root).visibility = View.GONE
                }
                animatorSet.playTogether(
                        ObjectAnimator.ofFloat(findViewById(R.id.input_root), "translationX", -width, 0.0f),
                        ObjectAnimator.ofFloat(findViewById(R.id.output_root), "translationX", 0.0f, width))
                animatorSet.start()
            } else {
                findViewById<View>(R.id.output_root).visibility = View.GONE
            }

            status = ScreenStatus.INPUT
        }
    }

    override fun showInputLoading() {
        findViewById<View>(R.id.input_cancel).visibility = View.GONE
        findViewById<View>(R.id.input_check).visibility = View.GONE
        findViewById<View>(R.id.input_load).visibility = View.VISIBLE
    }

    override fun showSave(viewModel: WalletViewModel) {
        findViewById<View>(R.id.root).post {
            val width = findViewById<View>(R.id.root).width.toFloat()

            findViewById<EditText>(R.id.input_address).setText(viewModel.address)
            findViewById<TextView>(R.id.output_address).text = viewModel.address
            findViewById<TextView>(R.id.output_balance).text = viewModel.balance
            findViewById<EditText>(R.id.output_name).setText(viewModel.name)

            findViewById<View>(R.id.input_root).visibility = View.VISIBLE
            findViewById<View>(R.id.output_root).visibility = View.VISIBLE

            findViewById<View>(R.id.output_change).visibility = View.VISIBLE
            findViewById<View>(R.id.output_save).visibility = View.VISIBLE
            findViewById<View>(R.id.output_load).visibility = View.GONE

            if (status == ScreenStatus.INPUT) {
                val animatorSet = AnimatorSet()
                animatorSet.duration = animDuration
                animatorSet.interpolator = AccelerateDecelerateInterpolator()
                animatorSet.addOnAnimationEndListener {
                    findViewById<View>(R.id.input_root).visibility = View.GONE
                }
                animatorSet.playTogether(
                        ObjectAnimator.ofFloat(findViewById(R.id.input_root), "translationX", 0.0f, -width),
                        ObjectAnimator.ofFloat(findViewById(R.id.output_root), "translationX", width, 0.0f))
                animatorSet.start()
            } else {
                findViewById<View>(R.id.input_root).visibility = View.GONE
            }

            status = ScreenStatus.SAVE
        }
    }

    override fun showSaveLoading() {
        findViewById<View>(R.id.output_change).visibility = View.GONE
        findViewById<View>(R.id.output_save).visibility = View.GONE
        findViewById<View>(R.id.output_load).visibility = View.VISIBLE
    }

    override fun showError(error: Throwable) {
        AlertDialog.Builder(this)
                .setMessage(error.localizedMessage)
                .setPositiveButton(R.string.app_ok, null)
                .show()
    }

    override fun showNonFatalErrorMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showTips() {
        findViewById<View>(R.id.input_hint).visibility = View.VISIBLE
        findViewById<View>(R.id.output_hint).visibility = View.VISIBLE
    }

    override fun hideTips() {
        findViewById<View>(R.id.input_hint).visibility = View.GONE
        findViewById<View>(R.id.output_hint).visibility = View.GONE
    }

    private enum class ScreenStatus {
        INITIAL, INPUT, SAVE
    }

}
