package ademar.bitac.ext

import android.animation.Animator
import android.animation.AnimatorSet
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.doAnswer
import org.mockito.MockitoAnnotations

class AnimatorSetTest {

    @Mock private lateinit var mockAnimatorSet: AnimatorSet
    @Mock private lateinit var mockAnimator: Animator

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun testAddOnAnimationEndListener() {
        var listener: Animator.AnimatorListener? = null
        doAnswer {
            listener = it.arguments[0] as Animator.AnimatorListener
            Unit
        }.whenever(mockAnimatorSet).addListener(any())

        var callCount = 0
        mockAnimatorSet.addOnAnimationEndListener { callCount++ }

        assertThat(listener).isNotNull

        listener!!.let {
            it.onAnimationStart(mockAnimator)
            it.onAnimationEnd(mockAnimator)

            assertThat(callCount).isEqualTo(1)
        }
    }

}
