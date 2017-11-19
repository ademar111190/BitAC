package ademar.bitac.view

import android.content.Context
import android.support.annotation.Keep
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator

@Keep // Used on Xml/Reflection
class ActionButtonBehavior : CoordinatorLayout.Behavior<View> {

    @Suppress("unused") // Xml/Reflection Load
    constructor() : super()

    @Suppress("unused") // Xml/Reflection Load
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun layoutDependsOn(parent: CoordinatorLayout?, child: View?, dependency: View?) = dependency is Snackbar.SnackbarLayout

    override fun onDependentViewChanged(parent: CoordinatorLayout?, child: View?, dependency: View?): Boolean {
        animate(child, -(dependency?.height?.toFloat() ?: 0.0f))
        return true
    }

    override fun onDependentViewRemoved(parent: CoordinatorLayout?, child: View?, dependency: View?) {
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
