package ademar.bitac.ext

import ademar.bitac.view.Theme
import android.content.Intent
import com.nhaarman.mockitokotlin2.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class IntentTest {

    @Mock private lateinit var mockIntent: Intent
    @Mock private lateinit var mockTheme: Theme

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        whenever(mockIntent.getSerializableExtra(EXTRA_THEME)).thenReturn(mockTheme)
    }

    @Test
    fun testGetTheme() {
        assertThat(mockIntent.getTheme()).isEqualTo(mockTheme)
    }

}
