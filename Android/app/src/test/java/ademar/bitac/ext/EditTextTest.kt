package ademar.bitac.ext

import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import org.mockito.kotlin.any
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class EditTextTest {

    @Mock private lateinit var mockEditText: EditText
    @Mock private lateinit var mockKeyEvent: KeyEvent

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun testSetActionNext() {
        var listener: TextView.OnEditorActionListener? = null
        doAnswer {
            listener = it.arguments[0] as TextView.OnEditorActionListener
        }.whenever(mockEditText).setOnEditorActionListener(any())

        var callCount = 0
        mockEditText.setActionNext {
            callCount++
        }

        assertThat(listener).isNotNull
        listener!!.onEditorAction(mockEditText, EditorInfo.IME_ACTION_NEXT, mockKeyEvent)
        listener!!.onEditorAction(mockEditText, EditorInfo.IME_ACTION_SEND, mockKeyEvent)
        listener!!.onEditorAction(mockEditText, EditorInfo.IME_NULL, mockKeyEvent)

        assertThat(callCount).isEqualTo(1)
    }

    @Test
    fun testSetActionSend() {
        var listener: TextView.OnEditorActionListener? = null
        doAnswer {
            listener = it.arguments[0] as TextView.OnEditorActionListener
        }.whenever(mockEditText).setOnEditorActionListener(any())

        var callCount = 0
        mockEditText.setActionSend {
            callCount++
        }

        assertThat(listener).isNotNull
        listener!!.onEditorAction(mockEditText, EditorInfo.IME_ACTION_NEXT, mockKeyEvent)
        listener!!.onEditorAction(mockEditText, EditorInfo.IME_ACTION_SEND, mockKeyEvent)
        listener!!.onEditorAction(mockEditText, EditorInfo.IME_NULL, mockKeyEvent)

        assertThat(callCount).isEqualTo(1)
    }

}
