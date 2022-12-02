package ademar.bitac.ext

import android.animation.Animator
import android.animation.AnimatorSet

fun AnimatorSet.addOnAnimationEndListener(listener: (animator: Animator) -> Unit) {
    addListener(object : Animator.AnimatorListener {
        override fun onAnimationRepeat(animator: Animator) {}

        override fun onAnimationEnd(animator: Animator) {
            listener(animator)
        }

        override fun onAnimationCancel(animator: Animator) {}

        override fun onAnimationStart(animator: Animator) {}
    })
}
