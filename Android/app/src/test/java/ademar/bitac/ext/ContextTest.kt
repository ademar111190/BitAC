package ademar.bitac.ext

import ademar.bitac.App
import android.app.Activity
import android.content.Context
import org.mockito.kotlin.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class ContextTest {

    @Mock private lateinit var mockContext: Context
    @Mock private lateinit var mockActivity: Activity
    @Mock private lateinit var mockApp: App

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        whenever(mockContext.applicationContext).thenReturn(mockApp)
        whenever(mockActivity.applicationContext).thenReturn(mockApp)
        whenever(mockApp.applicationContext).thenReturn(mockApp)
    }

    @Test
    fun testGetApp() {
        assertThat(mockContext.getApp()).isEqualTo(mockApp)
        assertThat(mockActivity.getApp()).isEqualTo(mockApp)
        assertThat(mockApp.getApp()).isEqualTo(mockApp)
    }

}
