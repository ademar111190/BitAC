package ademar.bitac.interactor.provider

import ademar.bitac.ext.KEY_PROVIDER_SETUP
import ademar.bitac.model.Currency
import ademar.bitac.model.Provider
import ademar.bitac.test.fixture.CurrencyFixture
import ademar.bitac.test.fixture.ProviderFixture
import android.content.Context
import android.content.SharedPreferences
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class DisableProviderTest {

    @Mock private lateinit var mockContext: Context
    @Mock private lateinit var mockGetEnabledProviders: GetEnabledProviders
    @Mock private lateinit var mockSharedPreferences: SharedPreferences
    @Mock private lateinit var mockEditor: SharedPreferences.Editor
    @Mock private lateinit var mockCurrency: Currency
    @Mock private lateinit var mockProvider: Provider

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        whenever(mockSharedPreferences.edit()).thenReturn(mockEditor)
        whenever(mockEditor.putStringSet(eq(KEY_PROVIDER_SETUP), any())).thenReturn(mockEditor)
        whenever(mockCurrency.id).thenReturn("mock_currency_id")
        whenever(mockProvider.id).thenReturn("mock_provider_id")
    }

    @Test
    fun testExecute_doesNotExists_empty() {
        whenever(mockGetEnabledProviders.execute()).thenReturn(setOf())
        val disableProvider = DisableProvider(mockContext, mockGetEnabledProviders)
        disableProvider.preference = mockSharedPreferences
        disableProvider.execute(mockCurrency, mockProvider)
        verify(mockEditor).putStringSet(KEY_PROVIDER_SETUP, setOf())
        verify(mockEditor).apply()
    }

    @Test
    fun testExecute_doesNotExists_notEmpty() {
        whenever(mockGetEnabledProviders.execute()).thenReturn(setOf(
                CurrencyFixture.makeModel() to ProviderFixture.makeModel()
        ))
        val disableProvider = DisableProvider(mockContext, mockGetEnabledProviders)
        disableProvider.preference = mockSharedPreferences
        disableProvider.execute(mockCurrency, mockProvider)
        verify(mockEditor).putStringSet(KEY_PROVIDER_SETUP, setOf("currency_id:provider_id"))
        verify(mockEditor).apply()
    }

    @Test
    fun testExecute_doesNotExists_hasKeyAndValue() {
        whenever(mockGetEnabledProviders.execute()).thenReturn(setOf(
                mockCurrency to ProviderFixture.makeModel(),
                CurrencyFixture.makeModel() to mockProvider
        ))
        val disableProvider = DisableProvider(mockContext, mockGetEnabledProviders)
        disableProvider.preference = mockSharedPreferences
        disableProvider.execute(mockCurrency, mockProvider)
        verify(mockEditor).putStringSet(KEY_PROVIDER_SETUP, setOf("mock_currency_id:provider_id", "currency_id:mock_provider_id"))
        verify(mockEditor).apply()
    }

    @Test
    fun testExecute_unique() {
        whenever(mockGetEnabledProviders.execute()).thenReturn(setOf(
                mockCurrency to mockProvider
        ))
        val disableProvider = DisableProvider(mockContext, mockGetEnabledProviders)
        disableProvider.preference = mockSharedPreferences
        disableProvider.execute(mockCurrency, mockProvider)
        verify(mockEditor).putStringSet(KEY_PROVIDER_SETUP, setOf())
        verify(mockEditor).apply()
    }

    @Test
    fun testExecute_assorted() {
        whenever(mockGetEnabledProviders.execute()).thenReturn(setOf(
                mockCurrency to mockProvider,
                CurrencyFixture.makeModel() to ProviderFixture.makeModel()
        ))
        val disableProvider = DisableProvider(mockContext, mockGetEnabledProviders)
        disableProvider.preference = mockSharedPreferences
        disableProvider.execute(mockCurrency, mockProvider)
        verify(mockEditor).putStringSet(KEY_PROVIDER_SETUP, setOf("currency_id:provider_id"))
        verify(mockEditor).apply()
    }

    @Test
    fun testExecute_multiples() {
        whenever(mockGetEnabledProviders.execute()).thenReturn(setOf(
                mockCurrency to mockProvider,
                CurrencyFixture.makeModel() to ProviderFixture.makeModel(),
                mockCurrency to ProviderFixture.makeModel(),
                CurrencyFixture.makeModel() to mockProvider,
                mockCurrency to mockProvider,
                CurrencyFixture.makeModel() to ProviderFixture.makeModel(),
                mockCurrency to ProviderFixture.makeModel(),
                CurrencyFixture.makeModel() to mockProvider
        ))
        val disableProvider = DisableProvider(mockContext, mockGetEnabledProviders)
        disableProvider.preference = mockSharedPreferences
        disableProvider.execute(mockCurrency, mockProvider)
        verify(mockEditor).putStringSet(KEY_PROVIDER_SETUP, setOf("currency_id:provider_id", "mock_currency_id:provider_id", "currency_id:mock_provider_id"))
        verify(mockEditor).apply()
    }

}
