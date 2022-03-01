package ademar.bitac.presenter

import ademar.bitac.R
import ademar.bitac.interactor.BitcoinUri
import ademar.bitac.interactor.wallet.AddWallet
import ademar.bitac.interactor.wallet.CleanWalletName
import ademar.bitac.interactor.wallet.GetAddressData
import ademar.bitac.interactor.wallet.GetWalletsCount
import ademar.bitac.model.StandardErrors
import ademar.bitac.test.fixture.AddressFixture
import ademar.bitac.test.fixture.WalletViewModelFixture
import ademar.bitac.viewmodel.WalletMapper
import ademar.bitac.viewmodel.WalletViewModel
import android.app.Activity
import android.content.Context
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class CheckAddressPresenterTest {

    @Mock private lateinit var mockContext: Context
    @Mock private lateinit var mockActivity: Activity
    @Mock private lateinit var mockBitcoinUri: BitcoinUri
    @Mock private lateinit var mockCleanWalletName: CleanWalletName
    @Mock private lateinit var mockGetAddressData: GetAddressData
    @Mock private lateinit var mockGetWalletsCount: GetWalletsCount
    @Mock private lateinit var mockAddWallet: AddWallet
    @Mock private lateinit var mockWalletMapper: WalletMapper
    @Mock private lateinit var mockStandardErrors: StandardErrors
    @Mock private lateinit var mockException: Exception
    @Mock private lateinit var mockHumanException: Exception

    private val action = "An action"
    private val address = "1DPYudPDKLxnFkTtDUbWrEZZhfbuHoWgX8"
    private val addressFailure = "An address failure message"
    private val defaultWalletName = "An wallet name"
    private val invalidAddress = "an invalid address message"
    private val label = "a label"
    private val name = "a name"
    private val cleanedName = "a clean name"
    private val defaultWalletViewModel = WalletViewModel(0L, "", "", "", "", "")

    private var hideTipsCount = 0
    private var showErrorCount = 0
    private var showInputCount = 0
    private var showInputLoadingCount = 0
    private var showNonFatalErrorMessageCount = 0
    private var showSaveCount = 0
    private var showSaveLoadingCount = 0
    private var showTipsCount = 0

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)

        RxAndroidPlugins.setMainThreadSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }

        whenever(mockContext.getString(R.string.check_address_qr_code_fail)).thenReturn(addressFailure)
        whenever(mockContext.getString(R.string.check_address_invalid_address)).thenReturn(invalidAddress)
        whenever(mockContext.getString(R.string.app_unnamed)).thenReturn(defaultWalletName)
        whenever(mockStandardErrors.humanReadableMessage(mockException)).thenReturn(mockHumanException)

        hideTipsCount = 0
        showErrorCount = 0
        showInputCount = 0
        showInputLoadingCount = 0
        showNonFatalErrorMessageCount = 0
        showSaveCount = 0
        showSaveLoadingCount = 0
        showTipsCount = 0
    }

    @After
    fun tearDown() {
        RxAndroidPlugins.reset()
        RxJavaPlugins.reset()
    }

    @Test
    fun testLoadDataNoWallets() {
        val view = object : StubCheckAddressView() {
            override fun showInput(viewModel: WalletViewModel) {
                assertThat(viewModel).isEqualTo(defaultWalletViewModel)
                showInputCount++
            }

            override fun showTips() {
                showTipsCount++
            }
        }

        whenever(mockGetWalletsCount.execute()).thenReturn(Single.just(0))

        val presenter = CheckAddressPresenter(mockContext, mockActivity, mockBitcoinUri, mockCleanWalletName, mockGetAddressData, mockGetWalletsCount, mockAddWallet, mockWalletMapper, mockStandardErrors)
        presenter.view = view
        presenter.loadData()

        assertThat(showInputCount).isEqualTo(1)
        assertThat(showTipsCount).isEqualTo(1)
    }

    @Test
    fun testLoadDataOneWallet() {
        val view = object : StubCheckAddressView() {
            override fun showInput(viewModel: WalletViewModel) {
                assertThat(viewModel).isEqualTo(defaultWalletViewModel)
                showInputCount++
            }

            override fun hideTips() {
                hideTipsCount++
            }
        }

        whenever(mockGetWalletsCount.execute()).thenReturn(Single.just(1))

        val presenter = CheckAddressPresenter(mockContext, mockActivity, mockBitcoinUri, mockCleanWalletName, mockGetAddressData, mockGetWalletsCount, mockAddWallet, mockWalletMapper, mockStandardErrors)
        presenter.view = view
        presenter.loadData()

        assertThat(showInputCount).isEqualTo(1)
        assertThat(hideTipsCount).isEqualTo(1)
    }

    @Test
    fun testLoadDataMultipleWallets() {
        val view = object : StubCheckAddressView() {
            override fun showInput(viewModel: WalletViewModel) {
                assertThat(viewModel).isEqualTo(defaultWalletViewModel)
                showInputCount++
            }

            override fun hideTips() {
                hideTipsCount++
            }
        }

        whenever(mockGetWalletsCount.execute()).thenReturn(Single.just(100))

        val presenter = CheckAddressPresenter(mockContext, mockActivity, mockBitcoinUri, mockCleanWalletName, mockGetAddressData, mockGetWalletsCount, mockAddWallet, mockWalletMapper, mockStandardErrors)
        presenter.view = view
        presenter.loadData()

        assertThat(showInputCount).isEqualTo(1)
        assertThat(hideTipsCount).isEqualTo(1)
    }

    @Test
    fun testLoadDataException() {
        val view = object : StubCheckAddressView() {
            override fun showInput(viewModel: WalletViewModel) {
                assertThat(viewModel).isEqualTo(defaultWalletViewModel)
                showInputCount++
            }
        }

        whenever(mockGetWalletsCount.execute()).thenReturn(Single.error(mockException))

        val presenter = CheckAddressPresenter(mockContext, mockActivity, mockBitcoinUri, mockCleanWalletName, mockGetAddressData, mockGetWalletsCount, mockAddWallet, mockWalletMapper, mockStandardErrors)
        presenter.view = view
        presenter.loadData()

        assertThat(showInputCount).isEqualTo(1)
    }

    @Test
    fun testCancel() {
        val presenter = CheckAddressPresenter(mockContext, mockActivity, mockBitcoinUri, mockCleanWalletName, mockGetAddressData, mockGetWalletsCount, mockAddWallet, mockWalletMapper, mockStandardErrors)
        presenter.view = StubCheckAddressView()
        presenter.cancel()

        verify(mockActivity).finish()
    }

    @Test
    fun testParseActionNullAddress() {
        val view = object : StubCheckAddressView() {
            override fun showInput(viewModel: WalletViewModel) {
                assertThat(viewModel).isEqualTo(defaultWalletViewModel)
                showInputCount++
            }

            override fun showNonFatalErrorMessage(message: String) {
                assertThat(message).isEqualTo(addressFailure)
                showNonFatalErrorMessageCount++
            }
        }

        whenever(mockBitcoinUri.getAddress(action)).thenReturn(null)

        val presenter = CheckAddressPresenter(mockContext, mockActivity, mockBitcoinUri, mockCleanWalletName, mockGetAddressData, mockGetWalletsCount, mockAddWallet, mockWalletMapper, mockStandardErrors)
        presenter.view = view
        presenter.parseAction(action)

        assertThat(showNonFatalErrorMessageCount).isEqualTo(1)
    }

    @Test
    fun testParseActionValidAddress() {
        val view = object : StubCheckAddressView() {
            override fun showInput(viewModel: WalletViewModel) {
                assertThat(viewModel).isEqualTo(WalletViewModel(0L, label, address, "", "", ""))
                showInputCount++
            }
        }

        whenever(mockBitcoinUri.getAddress(action)).thenReturn(address)
        whenever(mockBitcoinUri.getLabel(action)).thenReturn(label)
        whenever(mockCleanWalletName.execute(label, "")).thenReturn(label)

        val presenter = CheckAddressPresenter(mockContext, mockActivity, mockBitcoinUri, mockCleanWalletName, mockGetAddressData, mockGetWalletsCount, mockAddWallet, mockWalletMapper, mockStandardErrors)
        presenter.view = view
        presenter.parseAction(action)

    }

    @Test
    fun testCheckNullAddress() {
        val view = object : StubCheckAddressView() {
            override fun showError(error: Throwable) {
                assertThat(error.message).isEqualTo(invalidAddress)
                showErrorCount++
            }
        }

        val presenter = CheckAddressPresenter(mockContext, mockActivity, mockBitcoinUri, mockCleanWalletName, mockGetAddressData, mockGetWalletsCount, mockAddWallet, mockWalletMapper, mockStandardErrors)
        presenter.view = view
        presenter.check(null)

        assertThat(showErrorCount).isEqualTo(1)
    }

    @Test
    fun testCheckBlankAddress() {
        val view = object : StubCheckAddressView() {
            override fun showError(error: Throwable) {
                assertThat(error.message).isEqualTo(invalidAddress)
                showErrorCount++
            }
        }

        val presenter = CheckAddressPresenter(mockContext, mockActivity, mockBitcoinUri, mockCleanWalletName, mockGetAddressData, mockGetWalletsCount, mockAddWallet, mockWalletMapper, mockStandardErrors)
        presenter.view = view
        presenter.check(" ")

        assertThat(showErrorCount).isEqualTo(1)
    }

    @Test
    fun testCheckValidAddressDataFailure() {
        val view = object : StubCheckAddressView() {
            override fun showInputLoading() {
                showInputLoadingCount++
            }

            override fun showError(error: Throwable) {
                assertThat(error).isEqualTo(mockException)
                showErrorCount++
            }

            override fun showInput(viewModel: WalletViewModel) {
                assertThat(viewModel).isEqualTo(WalletViewModel(0L, "", address, "", "", ""))
                showInputCount++
            }
        }

        whenever(mockGetAddressData.execute(address)).thenReturn(Single.error(mockException))

        val presenter = CheckAddressPresenter(mockContext, mockActivity, mockBitcoinUri, mockCleanWalletName, mockGetAddressData, mockGetWalletsCount, mockAddWallet, mockWalletMapper, mockStandardErrors)
        presenter.view = view
        presenter.check(address)

        assertThat(showInputLoadingCount).isEqualTo(1)
        assertThat(showErrorCount).isEqualTo(1)
        assertThat(showInputCount).isEqualTo(1)
    }

    @Test
    fun testCheckValidAddress() {
        val addressModel = AddressFixture.makeModel()
        val walletViewModel = WalletViewModelFixture.makeModel()
        val view = object : StubCheckAddressView() {
            override fun showInputLoading() {
                showInputLoadingCount++
            }

            override fun showSave(viewModel: WalletViewModel) {
                assertThat(viewModel).isEqualTo(walletViewModel)
                showSaveCount++
            }
        }

        whenever(mockGetAddressData.execute(address)).thenReturn(Single.just(addressModel))
        whenever(mockWalletMapper.transform(defaultWalletViewModel, addressModel)).thenReturn(walletViewModel)

        val presenter = CheckAddressPresenter(mockContext, mockActivity, mockBitcoinUri, mockCleanWalletName, mockGetAddressData, mockGetWalletsCount, mockAddWallet, mockWalletMapper, mockStandardErrors)
        presenter.view = view
        presenter.check(address)

        assertThat(showInputLoadingCount).isEqualTo(1)
        assertThat(showSaveCount).isEqualTo(1)
    }

    @Test
    fun testChange() {
        val view = object : StubCheckAddressView() {
            override fun showInput(viewModel: WalletViewModel) {
                assertThat(viewModel).isEqualTo(defaultWalletViewModel)
                showInputCount++
            }
        }

        val presenter = CheckAddressPresenter(mockContext, mockActivity, mockBitcoinUri, mockCleanWalletName, mockGetAddressData, mockGetWalletsCount, mockAddWallet, mockWalletMapper, mockStandardErrors)
        presenter.view = view
        presenter.change()

        assertThat(showInputCount).isEqualTo(1)
    }

    @Test
    fun testSave() {
        val view = object : StubCheckAddressView() {
            override fun showSaveLoading() {
                showSaveLoadingCount++
            }
        }

        whenever(mockCleanWalletName.execute(name, defaultWalletName)).thenReturn(cleanedName)
        whenever(mockAddWallet.execute(cleanedName, "", 0L)).thenReturn(Completable.complete())

        val presenter = CheckAddressPresenter(mockContext, mockActivity, mockBitcoinUri, mockCleanWalletName, mockGetAddressData, mockGetWalletsCount, mockAddWallet, mockWalletMapper, mockStandardErrors)
        presenter.view = view
        presenter.save(name)

        assertThat(showSaveLoadingCount).isEqualTo(1)
        verify(mockActivity).finish()
    }

    @Test
    fun testSaveNullName() {
        val view = object : StubCheckAddressView() {
            override fun showSaveLoading() {
                showSaveLoadingCount++
            }
        }

        whenever(mockCleanWalletName.execute(null, defaultWalletName)).thenReturn(cleanedName)
        whenever(mockAddWallet.execute(cleanedName, "", 0L)).thenReturn(Completable.complete())

        val presenter = CheckAddressPresenter(mockContext, mockActivity, mockBitcoinUri, mockCleanWalletName, mockGetAddressData, mockGetWalletsCount, mockAddWallet, mockWalletMapper, mockStandardErrors)
        presenter.view = view
        presenter.save(null)

        assertThat(showSaveLoadingCount).isEqualTo(1)
        verify(mockActivity).finish()
    }

    @Test
    fun testSaveException() {
        val view = object : StubCheckAddressView() {
            override fun showSaveLoading() {
                showSaveLoadingCount++
            }

            override fun showError(error: Throwable) {
                assertThat(error).isEqualTo(mockHumanException)
                showErrorCount++
            }

            override fun showSave(viewModel: WalletViewModel) {
                assertThat(viewModel).isEqualTo(WalletViewModel(0L, cleanedName, "", "", "", ""))
                showSaveCount++
            }
        }

        whenever(mockCleanWalletName.execute(name, defaultWalletName)).thenReturn(cleanedName)
        whenever(mockAddWallet.execute(cleanedName, "", 0L)).thenReturn(Completable.error(mockException))

        val presenter = CheckAddressPresenter(mockContext, mockActivity, mockBitcoinUri, mockCleanWalletName, mockGetAddressData, mockGetWalletsCount, mockAddWallet, mockWalletMapper, mockStandardErrors)
        presenter.view = view
        presenter.save(name)

        assertThat(showSaveLoadingCount).isEqualTo(1)
        assertThat(showErrorCount).isEqualTo(1)
        assertThat(showSaveCount).isEqualTo(1)
    }

    @Test
    fun testSaveNullNameException() {
        val view = object : StubCheckAddressView() {
            override fun showSaveLoading() {
                showSaveLoadingCount++
            }

            override fun showError(error: Throwable) {
                assertThat(error).isEqualTo(mockHumanException)
                showErrorCount++
            }

            override fun showSave(viewModel: WalletViewModel) {
                assertThat(viewModel).isEqualTo(WalletViewModel(0L, cleanedName, "", "", "", ""))
                showSaveCount++
            }
        }

        whenever(mockCleanWalletName.execute(null, defaultWalletName)).thenReturn(cleanedName)
        whenever(mockAddWallet.execute(cleanedName, "", 0L)).thenReturn(Completable.error(mockException))

        val presenter = CheckAddressPresenter(mockContext, mockActivity, mockBitcoinUri, mockCleanWalletName, mockGetAddressData, mockGetWalletsCount, mockAddWallet, mockWalletMapper, mockStandardErrors)
        presenter.view = view
        presenter.save(null)

        assertThat(showSaveLoadingCount).isEqualTo(1)
        assertThat(showErrorCount).isEqualTo(1)
        assertThat(showSaveCount).isEqualTo(1)
    }

}
