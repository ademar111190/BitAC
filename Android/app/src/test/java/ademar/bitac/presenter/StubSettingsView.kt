package ademar.bitac.presenter

import junit.framework.Assert.fail

open class StubSettingsView : SettingsView {

    override fun showError(throwable: Throwable) = fail("should not call showError. throwable: $throwable")

}
