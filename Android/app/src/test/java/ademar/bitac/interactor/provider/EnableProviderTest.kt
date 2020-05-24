package ademar.bitac.interactor.provider

import ademar.bitac.R
import ademar.bitac.ext.KEY_PROVIDER_SETUP
import ademar.bitac.model.Currency
import ademar.bitac.model.Provider
import ademar.bitac.test.fixture.CurrencyFixture
import ademar.bitac.test.fixture.ProviderFixture
import android.content.Context
import android.content.SharedPreferences
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class EnableProviderTest {

    @Mock private lateinit var mockContext: Context
    @Mock private lateinit var mockGetEnabledProviders: GetEnabledProviders
    @Mock private lateinit var mockGetProviders: GetProviders
    @Mock private lateinit var mockGetCurrencies: GetCurrencies
    @Mock private lateinit var mockSharedPreferences: SharedPreferences
    @Mock private lateinit var mockEditor: SharedPreferences.Editor
    @Mock private lateinit var mockCurrency: Currency
    @Mock private lateinit var mockProvider: Provider

    private val defaultCurrencyId = "default_currency_id"
    private val defaultProviderId = "default_provider_id"

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        whenever(mockSharedPreferences.edit()).thenReturn(mockEditor)
        whenever(mockEditor.putStringSet(eq(KEY_PROVIDER_SETUP), any())).thenReturn(mockEditor)
        whenever(mockCurrency.id).thenReturn("mock_currency_id")
        whenever(mockProvider.id).thenReturn("mock_provider_id")
        whenever(mockContext.getString(R.string.default_currency)).thenReturn(defaultCurrencyId)
        whenever(mockContext.getString(R.string.default_provider)).thenReturn(defaultProviderId)
        whenever(mockGetCurrencies.execute(defaultCurrencyId)).thenReturn(mockCurrency)
        whenever(mockGetProviders.execute(defaultProviderId)).thenReturn(mockProvider)
    }

    @Test
    fun testExecute_defaults() {
        whenever(mockGetEnabledProviders.execute()).thenReturn(setOf())
        val enableProvider = EnableProvider(mockContext, mockGetEnabledProviders, mockGetCurrencies, mockGetProviders)
        enableProvider.preference = mockSharedPreferences
        val defaults = enableProvider.execute()
        assertThat(defaults).containsExactly(mockCurrency to mockProvider)
        verify(mockEditor).putStringSet(KEY_PROVIDER_SETUP, setOf("mock_currency_id:mock_provider_id"))
        verify(mockEditor).apply()
    }

    @Test
    fun testExecute_doesNotExists_empty() {
        whenever(mockGetEnabledProviders.execute()).thenReturn(setOf())
        val enableProvider = EnableProvider(mockContext, mockGetEnabledProviders, mockGetCurrencies, mockGetProviders)
        enableProvider.preference = mockSharedPreferences
        enableProvider.execute(mockCurrency, mockProvider)
        verify(mockEditor).putStringSet(KEY_PROVIDER_SETUP, setOf("mock_currency_id:mock_provider_id"))
        verify(mockEditor).apply()
    }

    @Test
    fun testExecute_doesNotExists_notEmpty() {
        whenever(mockGetEnabledProviders.execute()).thenReturn(setOf(
                CurrencyFixture.makeModel() to ProviderFixture.makeModel()
        ))
        val enableProvider = EnableProvider(mockContext, mockGetEnabledProviders, mockGetCurrencies, mockGetProviders)
        enableProvider.preference = mockSharedPreferences
        enableProvider.execute(mockCurrency, mockProvider)
        verify(mockEditor).putStringSet(KEY_PROVIDER_SETUP, setOf("currency_id:provider_id", "mock_currency_id:mock_provider_id"))
        verify(mockEditor).apply()
    }

    @Test
    fun testExecute_doesNotExists_hasKeyAndValue() {
        whenever(mockGetEnabledProviders.execute()).thenReturn(setOf(
                mockCurrency to ProviderFixture.makeModel(),
                CurrencyFixture.makeModel() to mockProvider
        ))
        val enableProvider = EnableProvider(mockContext, mockGetEnabledProviders, mockGetCurrencies, mockGetProviders)
        enableProvider.preference = mockSharedPreferences
        enableProvider.execute(mockCurrency, mockProvider)
        verify(mockEditor).putStringSet(KEY_PROVIDER_SETUP, setOf("mock_currency_id:provider_id", "currency_id:mock_provider_id", "mock_currency_id:mock_provider_id"))
        verify(mockEditor).apply()
    }

    @Test
    fun testExecute_unique() {
        whenever(mockGetEnabledProviders.execute()).thenReturn(setOf(
                mockCurrency to mockProvider
        ))
        val enableProvider = EnableProvider(mockContext, mockGetEnabledProviders, mockGetCurrencies, mockGetProviders)
        enableProvider.preference = mockSharedPreferences
        enableProvider.execute(mockCurrency, mockProvider)
        verify(mockEditor).putStringSet(KEY_PROVIDER_SETUP, setOf("mock_currency_id:mock_provider_id"))
        verify(mockEditor).apply()
    }

    @Test
    fun testExecute_assorted() {
        whenever(mockGetEnabledProviders.execute()).thenReturn(setOf(
                mockCurrency to mockProvider,
                CurrencyFixture.makeModel() to ProviderFixture.makeModel()
        ))
        val enableProvider = EnableProvider(mockContext, mockGetEnabledProviders, mockGetCurrencies, mockGetProviders)
        enableProvider.preference = mockSharedPreferences
        enableProvider.execute(mockCurrency, mockProvider)
        verify(mockEditor).putStringSet(KEY_PROVIDER_SETUP, setOf("currency_id:provider_id", "mock_currency_id:mock_provider_id"))
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
        val enableProvider = EnableProvider(mockContext, mockGetEnabledProviders, mockGetCurrencies, mockGetProviders)
        enableProvider.preference = mockSharedPreferences
        enableProvider.execute(mockCurrency, mockProvider)
        verify(mockEditor).putStringSet(KEY_PROVIDER_SETUP, setOf("currency_id:provider_id", "mock_currency_id:provider_id", "currency_id:mock_provider_id", "mock_currency_id:mock_provider_id"))
        verify(mockEditor).apply()
    }

}
