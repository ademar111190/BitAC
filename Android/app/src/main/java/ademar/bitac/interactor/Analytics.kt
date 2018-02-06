package ademar.bitac.interactor

import ademar.bitac.model.Currency
import ademar.bitac.model.Provider
import ademar.bitac.view.Theme
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.answers.Answers
import com.crashlytics.android.answers.CustomEvent
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Analytics @Inject constructor(

        private val answers: Answers

) {

    fun trackError(e: Throwable) {
        Crashlytics.logException(e)
    }

    fun trackStart(walletsCount: Int, theme: Theme) {
        answers.logCustom(CustomEvent("Start")
                .putCustomAttribute("walletCount", walletsCount)
                .putCustomAttribute("theme", theme.tag))
    }

    fun trackReloadAction(source: ReloadActionSource) {
        answers.logCustom(CustomEvent("Reload")
                .putCustomAttribute("source", source.tag))
    }

    fun trackThemeChange(theme: Theme) {
        answers.logCustom(CustomEvent("ThemeChange")
                .putCustomAttribute("theme", theme.tag))
    }

    fun trackCopy(data: CopyData) {
        answers.logCustom(CustomEvent("Copy")
                .putCustomAttribute("data", data.tag))
    }

    fun trackDeleteAddress() {
        answers.logCustom(CustomEvent("DeleteAddress"))
    }

    fun trackUndoDeleteAddress() {
        answers.logCustom(CustomEvent("UndoDeleteAddress"))
    }

    fun trackVerifyAddressOpen() {
        answers.logCustom(CustomEvent("VerifyAddressOpen"))
    }

    fun trackVerifyAddressCancel() {
        answers.logCustom(CustomEvent("VerifyAddressCancel"))
    }

    fun trackVerifyAddressChange() {
        answers.logCustom(CustomEvent("VerifyAddressChange"))
    }

    fun trackVerifyAddressVerify(e: Throwable? = null) {
        answers.logCustom(CustomEvent("VerifyAddressSubmit")
                .putCustomAttribute("success", if (e == null) "yes" else "no")
                .putCustomAttribute("error", if (e == null) "none" else (e.message ?: "Unknown")))
    }

    fun trackVerifyQrCode(success: Boolean) {
        answers.logCustom(CustomEvent("VerifyAddressQrCode")
                .putCustomAttribute("success", if (success) "yes" else "no"))
    }

    fun trackVerifyAddressSave(hasCustomName: Boolean, e: Throwable? = null) {
        answers.logCustom(CustomEvent("VerifyAddressSave")
                .putCustomAttribute("success", if (e == null) "yes" else "no")
                .putCustomAttribute("named", if (hasCustomName) "yes" else "no"))
    }

    fun trackAbout() {
        answers.logCustom(CustomEvent("About"))
    }

    fun trackSettings() {
        answers.logCustom(CustomEvent("Settings"))
    }

    fun trackAddConversion() {
        answers.logCustom(CustomEvent("AddConversionFirstStep"))
    }

    fun trackAddConversion(currency: Currency) {
        answers.logCustom(CustomEvent("AddConversionSecondStep")
                .putCustomAttribute("currency", currency.id))
    }

    fun trackAddConversion(currency: Currency, provider: Provider) {
        answers.logCustom(CustomEvent("AddConversionThirdStep")
                .putCustomAttribute("currency", currency.id)
                .putCustomAttribute("provider", provider.id))
    }

    fun trackRemoveConversion(currency: Currency, provider: Provider) {
        answers.logCustom(CustomEvent("RemoveConversion")
                .putCustomAttribute("currency", currency.id)
                .putCustomAttribute("provider", provider.id))
    }

    enum class ReloadActionSource(val tag: String) {
        MENU("menu"), SWIPE("swipe")
    }

    enum class CopyData(val tag: String) {
        ADDRESS("address"), BALANCE("balance")
    }

}
