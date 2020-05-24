package ademar.bitac.ext

import android.view.accessibility.AccessibilityManager
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

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
