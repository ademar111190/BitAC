package ademar.bitac.presenter

import ademar.bitac.R
import ademar.bitac.interactor.provider.*
import ademar.bitac.model.Currency
import ademar.bitac.model.Provider
import ademar.bitac.navigation.Navigator
import ademar.bitac.view.Theme
import android.content.Context
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.entry
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class SettingsPresenterTest {

    @Mock private lateinit var mockContext: Context
    @Mock private lateinit var mockNavigator: Navigator
    @Mock private lateinit var mockGetCurrencies: GetCurrencies
    @Mock private lateinit var mockGetProviders: GetProviders
    @Mock private lateinit var mockGetEnabledProviders: GetEnabledProviders
    @Mock private lateinit var mockDisableProvider: DisableProvider
    @Mock private lateinit var mockEnableProvider: EnableProvider
    @Mock private lateinit var mockCurrencyA: Currency
    @Mock private lateinit var mockProviderA: Provider
    @Mock private lateinit var mockCurrencyB: Currency
    @Mock private lateinit var mockProviderB: Provider

    private val mockCurrencyAId = "mockCurrencyAId"
    private val mockProviderAId = "mockProviderAId"
    private val mockCurrencyBId = "mockCurrencyBId"
    private val mockProviderBId = "mockProviderBId"
    private val mockCurrencyAName = "mockCurrencyAName"
    private val mockProviderAName = "mockProviderAName"
    private val mockCurrencyBName = "mockCurrencyBName"
    private val mockProviderBName = "mockProviderBName"
    private val mockExceptionMessage = "An exception message"

    private var addConversionCount = 0
    private var chooseCurrencyCount = 0
    private var chooseProviderCount = 0
    private var showErrorCount = 0

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        whenever(mockCurrencyA.id).thenReturn(mockCurrencyAId)
        whenever(mockProviderA.id).thenReturn(mockProviderAId)
        whenever(mockCurrencyB.id).thenReturn(mockCurrencyBId)
        whenever(mockProviderB.id).thenReturn(mockProviderBId)
        whenever(mockCurrencyA.name).thenReturn(mockCurrencyAName)
        whenever(mockProviderA.name).thenReturn(mockProviderAName)
        whenever(mockCurrencyB.name).thenReturn(mockCurrencyBName)
        whenever(mockProviderB.name).thenReturn(mockProviderBName)
        whenever(mockContext.getString(R.string.settings_add_conversion_repeated, mockCurrencyAName, mockProviderAName)).thenReturn(mockExceptionMessage)

        addConversionCount = 0
        chooseCurrencyCount = 0
        chooseProviderCount = 0
        showErrorCount = 0
    }

    @Test
    fun testAttach() {
        val view = object : StubSettingsView() {
        }

        val presenter = SettingsPresenter(mockContext, mockNavigator, mockGetCurrencies, mockGetProviders, mockGetEnabledProviders, mockDisableProvider, mockEnableProvider)
        presenter.view = view
    }

    @Test
    fun testDetach() {
        val presenter = SettingsPresenter(mockContext, mockNavigator, mockGetCurrencies, mockGetProviders, mockGetEnabledProviders, mockDisableProvider, mockEnableProvider)
        presenter.view = null
    }

    @Test
    fun testLoadData_empty() {
        val view = object : StubSettingsView() {
        }
        whenever(mockGetEnabledProviders.execute()).thenReturn(setOf())

        val presenter = SettingsPresenter(mockContext, mockNavigator, mockGetCurrencies, mockGetProviders, mockGetEnabledProviders, mockDisableProvider, mockEnableProvider)
        presenter.view = view
        presenter.loadData()
    }

    @Test
    fun testLoadData_single() {
        val view = object : StubSettingsView() {
            override fun addConversion(currency: Currency, provider: Provider) {
                assertThat(currency).isEqualTo(mockCurrencyA)
                assertThat(provider).isEqualTo(mockProviderA)
                addConversionCount++
            }
        }
        whenever(mockGetEnabledProviders.execute()).thenReturn(setOf(mockCurrencyA to mockProviderA))

        val presenter = SettingsPresenter(mockContext, mockNavigator, mockGetCurrencies, mockGetProviders, mockGetEnabledProviders, mockDisableProvider, mockEnableProvider)
        presenter.view = view
        presenter.loadData()

        assertThat(addConversionCount).isEqualTo(1)
    }

    @Test
    fun testLoadData_multiple() {
        val view = object : StubSettingsView() {
            override fun addConversion(currency: Currency, provider: Provider) {
                assertThat(currency).isIn(mockCurrencyA, mockCurrencyB)
                assertThat(provider).isIn(mockProviderA, mockProviderB)
                addConversionCount++
            }
        }
        whenever(mockGetEnabledProviders.execute()).thenReturn(setOf(mockCurrencyA to mockProviderA, mockCurrencyB to mockProviderB))

        val presenter = SettingsPresenter(mockContext, mockNavigator, mockGetCurrencies, mockGetProviders, mockGetEnabledProviders, mockDisableProvider, mockEnableProvider)
        presenter.view = view
        presenter.loadData()

        assertThat(addConversionCount).isEqualTo(2)
    }

    @Test
    fun testAbout() {
        val view = object : StubSettingsView() {
        }

        val presenter = SettingsPresenter(mockContext, mockNavigator, mockGetCurrencies, mockGetProviders, mockGetEnabledProviders, mockDisableProvider, mockEnableProvider)
        presenter.view = view
        presenter.about()

        verify(mockNavigator).launchAbout()
    }

    @Test
    fun testChangeTheme() {
        val view = object : StubSettingsView() {
        }

        val presenter = SettingsPresenter(mockContext, mockNavigator, mockGetCurrencies, mockGetProviders, mockGetEnabledProviders, mockDisableProvider, mockEnableProvider)
        presenter.view = view
        presenter.changeTheme(Theme.ELEVEN.tag)

        verify(mockNavigator).launchHome(Theme.ELEVEN)
    }

    @Test
    fun testAddConversion() {
        val view = object : StubSettingsView() {
            override fun chooseCurrency(currencies: Map<String, Currency>) {
                assertThat(currencies).containsExactly(entry(mockCurrencyAName, mockCurrencyA), entry(mockCurrencyBName, mockCurrencyB))
                chooseCurrencyCount++
            }
        }

        whenever(mockGetCurrencies.execute()).thenReturn(listOf(mockCurrencyA, mockCurrencyB))

        val presenter = SettingsPresenter(mockContext, mockNavigator, mockGetCurrencies, mockGetProviders, mockGetEnabledProviders, mockDisableProvider, mockEnableProvider)
        presenter.view = view
        presenter.addConversion()

        assertThat(chooseCurrencyCount).isEqualTo(1)
    }

    @Test
    fun testAddConversion_currency() {
        val view = object : StubSettingsView() {
            override fun chooseProvider(currency: Currency, providers: Map<String, Provider>) {
                assertThat(currency).isEqualTo(mockCurrencyA)
                assertThat(providers).containsExactly(entry(mockProviderAName, mockProviderA), entry(mockProviderBName, mockProviderB))
                chooseProviderCount++
            }
        }

        whenever(mockGetProviders.execute(mockCurrencyA)).thenReturn(listOf(mockProviderA, mockProviderB))

        val presenter = SettingsPresenter(mockContext, mockNavigator, mockGetCurrencies, mockGetProviders, mockGetEnabledProviders, mockDisableProvider, mockEnableProvider)
        presenter.view = view
        presenter.addConversion(mockCurrencyA)

        assertThat(chooseProviderCount).isEqualTo(1)
    }

    @Test
    fun testAddConversion_currency_provider_firstSet() {
        val view = object : StubSettingsView() {
            override fun addConversion(currency: Currency, provider: Provider) {
                assertThat(currency).isEqualTo(mockCurrencyA)
                assertThat(provider).isEqualTo(mockProviderA)
                addConversionCount++
            }
        }

        whenever(mockGetEnabledProviders.execute()).thenReturn(setOf())

        val presenter = SettingsPresenter(mockContext, mockNavigator, mockGetCurrencies, mockGetProviders, mockGetEnabledProviders, mockDisableProvider, mockEnableProvider)
        presenter.view = view
        presenter.addConversion(mockCurrencyA, mockProviderA)

        verify(mockEnableProvider).execute(mockCurrencyA, mockProviderA)
        assertThat(addConversionCount).isEqualTo(1)
    }

    @Test
    fun testAddConversion_currency_provider_newSet() {
        val view = object : StubSettingsView() {
            override fun addConversion(currency: Currency, provider: Provider) {
                assertThat(currency).isEqualTo(mockCurrencyA)
                assertThat(provider).isEqualTo(mockProviderA)
                addConversionCount++
            }
        }

        whenever(mockGetEnabledProviders.execute()).thenReturn(setOf(mockCurrencyB to mockProviderB))

        val presenter = SettingsPresenter(mockContext, mockNavigator, mockGetCurrencies, mockGetProviders, mockGetEnabledProviders, mockDisableProvider, mockEnableProvider)
        presenter.view = view
        presenter.addConversion(mockCurrencyA, mockProviderA)

        verify(mockEnableProvider).execute(mockCurrencyA, mockProviderA)
        assertThat(addConversionCount).isEqualTo(1)
    }

    @Test
    fun testAddConversion_currency_provider_repeated() {
        val view = object : StubSettingsView() {
            override fun showError(throwable: Throwable) {
                assertThat(throwable.message).isEqualTo(mockExceptionMessage)
                showErrorCount++
            }
        }

        whenever(mockGetEnabledProviders.execute()).thenReturn(setOf(mockCurrencyA to mockProviderA))

        val presenter = SettingsPresenter(mockContext, mockNavigator, mockGetCurrencies, mockGetProviders, mockGetEnabledProviders, mockDisableProvider, mockEnableProvider)
        presenter.view = view
        presenter.addConversion(mockCurrencyA, mockProviderA)

        assertThat(showErrorCount).isEqualTo(1)
    }

    @Test
    fun testRemoveConversion() {
        val view = object : StubSettingsView() {
        }

        val presenter = SettingsPresenter(mockContext, mockNavigator, mockGetCurrencies, mockGetProviders, mockGetEnabledProviders, mockDisableProvider, mockEnableProvider)
        presenter.view = view
        presenter.removeConversion(mockCurrencyA, mockProviderA)

        verify(mockDisableProvider).execute(mockCurrencyA, mockProviderA)
    }

}
