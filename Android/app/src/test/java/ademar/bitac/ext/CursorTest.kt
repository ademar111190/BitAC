package ademar.bitac.ext

import android.database.Cursor
import com.nhaarman.mockito_kotlin.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.lang.Exception

class CursorTest {

    @Mock private lateinit var mockCursor: Cursor

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testGetStringSuccess() {
        val key = "foo key"
        val value = "foo value"
        val index = 7

        whenever(mockCursor.getColumnIndex(key)).thenReturn(index)
        whenever(mockCursor.getString(index)).thenReturn(value)

        val founded = mockCursor.getString(key)

        assertThat(founded).isEqualTo(value)
    }

    @Test(expected = Exception::class)
    fun testGetStringColumnException() {
        val key = "foo key"

        whenever(mockCursor.getColumnIndex(key)).thenThrow(Exception("Foo Exception"))

        mockCursor.getString(key)
    }

    @Test(expected = Exception::class)
    fun testGetStringStringException() {
        val key = "foo key"
        val index = 7

        whenever(mockCursor.getColumnIndex(key)).thenReturn(index)
        whenever(mockCursor.getString(index)).thenThrow(Exception("Foo Exception"))

        mockCursor.getString(key)
    }

    @Test
    fun testGetLongSuccess() {
        val key = "foo key"
        val value = 56L
        val index = 7

        whenever(mockCursor.getColumnIndex(key)).thenReturn(index)
        whenever(mockCursor.getLong(index)).thenReturn(value)

        val founded = mockCursor.getLong(key)

        assertThat(founded).isEqualTo(value)
    }

    @Test(expected = Exception::class)
    fun testGetLongColumnException() {
        val key = "foo key"

        whenever(mockCursor.getColumnIndex(key)).thenThrow(Exception("Foo Exception"))

        mockCursor.getLong(key)
    }

    @Test(expected = Exception::class)
    fun testGetLongStringException() {
        val key = "foo key"
        val index = 7

        whenever(mockCursor.getColumnIndex(key)).thenReturn(index)
        whenever(mockCursor.getLong(index)).thenThrow(Exception("Foo Exception"))

        mockCursor.getLong(key)
    }

}
