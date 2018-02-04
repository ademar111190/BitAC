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
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_check_address.*
import kotlinx.android.synthetic.main.check_address_input.*
import kotlinx.android.synthetic.main.check_address_output.*
import javax.inject.Inject

class CheckAddressActivity : AppCompatActivity(), CheckAddressView {

    @Inject lateinit var presenter: CheckAddressPresenter

    private var status = ScreenStatus.INITIAL

    private val qrReader by lazy {
        IntentIntegrator(this)
                .setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES)
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

        toolbar.setNavigationOnClickListener { finish() }
        input_qr_code.setOnClickListener { qrReader.initiateScan() }

        presenter.view = this
        presenter.loadData()

        input_cancel.setOnClickListener { presenter.cancel() }
        input_check.setOnClickListener { presenter.check(input_address.text.toString()) }
        output_change.setOnClickListener { presenter.change() }
        output_save.setOnClickListener { presenter.save(output_name.text.toString()) }
        input_address.setActionNext { presenter.check(input_address.text.toString()) }
        output_name.setActionSend { presenter.save(output_name.text.toString()) }
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
        root.post {
            val width = root.width.toFloat()

            input_address.setText(viewModel.address)
            output_address.text = viewModel.address
            output_balance.text = viewModel.balance
            output_name.setText(viewModel.name)

            input_root.visibility = View.VISIBLE
            output_root.visibility = View.VISIBLE

            input_cancel.visibility = View.VISIBLE
            input_check.visibility = View.VISIBLE
            input_load.visibility = View.GONE

            if (status == ScreenStatus.SAVE) {
                val animatorSet = AnimatorSet()
                animatorSet.duration = animDuration
                animatorSet.interpolator = AccelerateDecelerateInterpolator()
                animatorSet.addOnAnimationEndListener {
                    output_root.visibility = View.GONE
                }
                animatorSet.playTogether(
                        ObjectAnimator.ofFloat(input_root, "translationX", -width, 0.0f),
                        ObjectAnimator.ofFloat(output_root, "translationX", 0.0f, width))
                animatorSet.start()
            } else {
                output_root.visibility = View.GONE
            }

            status = ScreenStatus.INPUT
        }
    }

    override fun showInputLoading() {
        input_cancel.visibility = View.GONE
        input_check.visibility = View.GONE
        input_load.visibility = View.VISIBLE
    }

    override fun showSave(viewModel: WalletViewModel) {
        root.post {
            val width = root.width.toFloat()

            input_address.setText(viewModel.address)
            output_address.text = viewModel.address
            output_balance.text = viewModel.balance
            output_name.setText(viewModel.name)

            input_root.visibility = View.VISIBLE
            output_root.visibility = View.VISIBLE

            output_change.visibility = View.VISIBLE
            output_save.visibility = View.VISIBLE
            output_load.visibility = View.GONE

            if (status == ScreenStatus.INPUT) {
                val animatorSet = AnimatorSet()
                animatorSet.duration = animDuration
                animatorSet.interpolator = AccelerateDecelerateInterpolator()
                animatorSet.addOnAnimationEndListener {
                    input_root.visibility = View.GONE
                }
                animatorSet.playTogether(
                        ObjectAnimator.ofFloat(input_root, "translationX", 0.0f, -width),
                        ObjectAnimator.ofFloat(output_root, "translationX", width, 0.0f))
                animatorSet.start()
            } else {
                input_root.visibility = View.GONE
            }

            status = ScreenStatus.SAVE
        }
    }

    override fun showSaveLoading() {
        output_change.visibility = View.GONE
        output_save.visibility = View.GONE
        output_load.visibility = View.VISIBLE
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
        input_hint.visibility = View.VISIBLE
        output_hint.visibility = View.VISIBLE
    }

    override fun hideTips() {
        input_hint.visibility = View.GONE
        output_hint.visibility = View.GONE
    }

    private enum class ScreenStatus {
        INITIAL, INPUT, SAVE
    }

}
