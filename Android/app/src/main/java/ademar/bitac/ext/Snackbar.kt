package ademar.bitac.ext

import android.support.design.widget.BaseTransientBottomBar
import android.support.design.widget.Snackbar
import android.view.accessibility.AccessibilityManager

fun Snackbar.forceAnimation(): Snackbar {
    try {
        val mAccessibilityManagerField = BaseTransientBottomBar::class.java.getDeclaredField("mAccessibilityManager")
        mAccessibilityManagerField.isAccessible = true
        val accessibilityManager = mAccessibilityManagerField.get(this)
        val mIsEnabledField = AccessibilityManager::class.java.getDeclaredField("mIsEnabled")
        mIsEnabledField.isAccessible = true
        mIsEnabledField.setBoolean(accessibilityManager, false)
        mAccessibilityManagerField.set(this, accessibilityManager)
    } catch (_: Exception) {
    }
    return this
}
