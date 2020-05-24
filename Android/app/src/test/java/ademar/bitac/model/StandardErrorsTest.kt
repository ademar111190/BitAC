package ademar.bitac.model

import ademar.bitac.R
import android.content.Context
import com.nhaarman.mockitokotlin2.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class StandardErrorsTest {

    @Mock private lateinit var mockContext: Context

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        whenever(mockContext.getString(R.string.error_message_unknown)).thenReturn("UNKNOWN")
        whenever(mockContext.getString(R.string.error_message_unauthorized)).thenReturn("UNAUTHORIZED")
        whenever(mockContext.getString(R.string.error_message_no_connection)).thenReturn("NO_CONNECTION")
    }

    @Test
    fun testDefaults() {
        val standardErrors = StandardErrors(mockContext)
        val errors = listOf(
                standardErrors.unknown,
                standardErrors.unauthorized,
                standardErrors.noConnection)
        val errorsSet = hashSetOf(
                standardErrors.unknown,
                standardErrors.unauthorized,
                standardErrors.noConnection)
        assertThat(errors.size).isEqualTo(errorsSet.size)
        assertThat(StandardErrors(mockContext).unknown).isEqualTo(StandardErrors(mockContext).unknown)
        assertThat(StandardErrors(mockContext).unauthorized).isEqualTo(StandardErrors(mockContext).unauthorized)
        assertThat(StandardErrors(mockContext).noConnection).isEqualTo(StandardErrors(mockContext).noConnection)
    }

    @Test
    fun testProperties() {
        val standardErrors = StandardErrors(mockContext)
        val errors = listOf(
                standardErrors.unknown,
                standardErrors.unauthorized,
                standardErrors.noConnection)
        for ((i, a) in errors.withIndex()) {
            for ((j, b) in errors.withIndex()) {
                if (i == j) {
                    assertThat(a.message).isEqualTo(b.message)
                } else {
                    assertThat(a.message).isNotEqualTo(b.message)
                }
            }
        }
    }

    @Test
    fun testHumanReadableMessage() {
        val standardErrors = StandardErrors(mockContext)
        val error = standardErrors.humanReadableMessage(Exception("HUMAN_MESSAGE"))
        assertThat(error.message).isEqualTo("HUMAN_MESSAGE")
    }

}
