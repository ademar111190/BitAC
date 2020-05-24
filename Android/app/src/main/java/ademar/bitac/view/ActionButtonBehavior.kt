package ademar.bitac.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.annotation.Keep
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.snackbar.Snackbar

@Keep // Used on Xml/Reflection
class ActionButtonBehavior(
        context: Context?,
        attrs: AttributeSet?
) : CoordinatorLayout.Behavior<View>(context, attrs) {

    override fun layoutDependsOn(parent: CoordinatorLayout, child: View, dependency: View) = dependency is Snackbar.SnackbarLayout

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
        animate(child, -dependency.height.toFloat())
        return true
    }

    override fun onDependentViewRemoved(parent: CoordinatorLayout, child: View, dependency: View) {
        animate(child, 0.0f)
    }

    private fun animate(view: View?, y: Float) {
        if (view != null) {
            view.animation?.cancel()
            view.animate()
                    .translationY(y)
                    .setDuration(view.context.resources.getInteger(android.R.integer.config_shortAnimTime).toLong())
                    .setInterpolator(AccelerateDecelerateInterpolator())
                    .start()
        }
    }

}
