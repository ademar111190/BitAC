package ademar.bitac.interactor.provider

import ademar.bitac.ext.KEY_PROVIDER_FIRST_TIME
import ademar.bitac.ext.KEY_PROVIDER_SETUP
import ademar.bitac.model.Currency
import ademar.bitac.model.Provider
import ademar.bitac.test.fixture.CurrencyFixture
import ademar.bitac.test.fixture.ProviderFixture
import android.content.Context
import android.content.SharedPreferences
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class GetEnabledProvidersTest {

    @Mock private lateinit var mockContext: Context
    @Mock private lateinit var mockSharedPreferences: SharedPreferences
    @Mock private lateinit var mockEditor: SharedPreferences.Editor
    @Mock private lateinit var mockGetProviders: GetProviders
    @Mock private lateinit var mockGetCurrencies: GetCurrencies
    @Mock private lateinit var mockEnableProvider: EnableProvider
    @Mock private lateinit var mockCurrency: Currency
    @Mock private lateinit var mockProvider: Provider

    private val mockCurrencyId = "mock_currency_id"
    private val mockProviderId = "mock_provider_id"

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        whenever(mockSharedPreferences.edit()).thenReturn(mockEditor)
        whenever(mockGetCurrencies.execute(CurrencyFixture.id)).thenReturn(CurrencyFixture.makeModel())
        whenever(mockGetProviders.execute(ProviderFixture.id)).thenReturn(ProviderFixture.makeModel())
        whenever(mockGetCurrencies.execute(mockCurrencyId)).thenReturn(mockCurrency)
        whenever(mockGetProviders.execute(mockProviderId)).thenReturn(mockProvider)
    }

    @Test
    fun testExecute_firstTime() {
        val result = setOf(CurrencyFixture.makeModel() to ProviderFixture.makeModel())
        whenever(mockSharedPreferences.getBoolean(KEY_PROVIDER_FIRST_TIME, true)).thenReturn(true)
        whenever(mockEditor.putBoolean(KEY_PROVIDER_FIRST_TIME, false)).thenReturn(mockEditor)
        whenever(mockEnableProvider.execute()).thenReturn(result)

        val getEnabledProviders = GetEnabledProviders(mockContext, mockGetProviders, mockGetCurrencies)
        getEnabledProviders.preference = mockSharedPreferences
        getEnabledProviders.enabledProvider = mockEnableProvider
        assertThat(getEnabledProviders.execute()).isEqualTo(result)
        verify(mockEditor).putBoolean(KEY_PROVIDER_FIRST_TIME, false)
    }

    @Test
    fun testExecute_empty() {
        whenever(mockSharedPreferences.getBoolean(KEY_PROVIDER_FIRST_TIME, true)).thenReturn(false)
        whenever(mockSharedPreferences.getStringSet(KEY_PROVIDER_SETUP, setOf())).thenReturn(setOf())

        val getEnabledProviders = GetEnabledProviders(mockContext, mockGetProviders, mockGetCurrencies)
        getEnabledProviders.preference = mockSharedPreferences
        getEnabledProviders.enabledProvider = mockEnableProvider
        assertThat(getEnabledProviders.execute()).isEmpty()
    }

    @Test
    fun testExecute_single() {
        whenever(mockSharedPreferences.getBoolean(KEY_PROVIDER_FIRST_TIME, true)).thenReturn(false)
        whenever(mockSharedPreferences.getStringSet(KEY_PROVIDER_SETUP, setOf())).thenReturn(setOf(
                "${CurrencyFixture.id}:${ProviderFixture.id}"
        ))

        val getEnabledProviders = GetEnabledProviders(mockContext, mockGetProviders, mockGetCurrencies)
        getEnabledProviders.preference = mockSharedPreferences
        getEnabledProviders.enabledProvider = mockEnableProvider
        assertThat(getEnabledProviders.execute()).containsExactly(CurrencyFixture.makeModel() to ProviderFixture.makeModel())
    }

    @Test
    fun testExecute_multiple() {
        whenever(mockSharedPreferences.getBoolean(KEY_PROVIDER_FIRST_TIME, true)).thenReturn(false)
        whenever(mockSharedPreferences.getStringSet(KEY_PROVIDER_SETUP, setOf())).thenReturn(setOf(
                "${CurrencyFixture.id}:${ProviderFixture.id}",
                "$mockCurrencyId:$mockProviderId"
        ))

        val getEnabledProviders = GetEnabledProviders(mockContext, mockGetProviders, mockGetCurrencies)
        getEnabledProviders.preference = mockSharedPreferences
        getEnabledProviders.enabledProvider = mockEnableProvider
        assertThat(getEnabledProviders.execute()).containsExactly(CurrencyFixture.makeModel() to ProviderFixture.makeModel(), mockCurrency to mockProvider)
    }

}
