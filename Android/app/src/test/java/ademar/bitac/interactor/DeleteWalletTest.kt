package ademar.bitac.interactor

import ademar.bitac.repository.Repository
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class DeleteWalletTest {

    @Mock private lateinit var mockRepository: Repository
    @Mock private lateinit var mockWalletDeleteWatcher: WalletDeleteWatcher

    private val id = 123L

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testExecuteNamedArguments() {
        DeleteWallet(mockRepository, mockWalletDeleteWatcher)
                .execute(id)
                .test()
                .assertNoErrors()

        verify(mockRepository).deleteWallet(id)
        verify(mockWalletDeleteWatcher).notifyDataDeleted(id)
        verifyNoMoreInteractions(mockRepository, mockWalletDeleteWatcher)
    }

}
