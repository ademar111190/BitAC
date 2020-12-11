package ademar.bitac.presenter

import ademar.bitac.interactor.wallet.*
import ademar.bitac.model.StandardErrors
import ademar.bitac.navigation.Navigator
import ademar.bitac.test.fixture.WalletFixture
import ademar.bitac.test.fixture.WalletViewModelFixture
import ademar.bitac.viewmodel.WalletMapper
import ademar.bitac.viewmodel.WalletViewModel
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class HomePresenterTest {

    @Mock private lateinit var mockGetWallets: GetWallets
    @Mock private lateinit var mockGetWalletSum: GetWalletSum
    @Mock private lateinit var mockUpdateWallets: UpdateWallets
    @Mock private lateinit var mockWalletMapper: WalletMapper
    @Mock private lateinit var mockAddWallet: AddWallet
    @Mock private lateinit var mockGetWalletsCount: GetWalletsCount
    @Mock private lateinit var mockNavigator: Navigator
    @Mock private lateinit var mockWalletAddWatcher: WalletAddWatcher
    @Mock private lateinit var mockWalletChangeWatcher: WalletChangeWatcher
    @Mock private lateinit var mockWalletDeleteWatcher: WalletDeleteWatcher
    @Mock private lateinit var mockStandardErrors: StandardErrors
    @Mock private lateinit var mockErrorA: Exception
    @Mock private lateinit var mockErrorB: Exception
    @Mock private lateinit var mockHumanReadableErrorA: Exception
    @Mock private lateinit var mockHumanReadableErrorB: Exception

    private val walletA = WalletFixture.makeModel()
    private val walletB = WalletFixture.makeModel(WalletFixture.id + 1L)
    private val walletViewModelA = WalletViewModelFixture.makeModel()
    private val walletViewModelB = WalletViewModelFixture.makeModel(WalletViewModelFixture.id + 1L)

    private var addWalletCount = 0
    private var deleteWalletCount = 0
    private var removeWalletCount = 0
    private var showContentCount = 0
    private var showErrorCount = 0
    private var showLoadingCount = 0
    private var showRefreshingCount = 0
    private var showRetryCount = 0

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        RxAndroidPlugins.setMainThreadSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }

        whenever(mockWalletAddWatcher.observe()).thenReturn(Observable.empty())
        whenever(mockWalletChangeWatcher.observe()).thenReturn(Observable.empty())
        whenever(mockWalletDeleteWatcher.observe()).thenReturn(Observable.empty())
        whenever(mockStandardErrors.humanReadableMessage(mockErrorA)).thenReturn(mockHumanReadableErrorA)
        whenever(mockStandardErrors.humanReadableMessage(mockErrorB)).thenReturn(mockHumanReadableErrorB)
        whenever(mockWalletMapper.transform(walletA)).thenReturn(walletViewModelA)
        whenever(mockWalletMapper.transform(walletB)).thenReturn(walletViewModelB)
        whenever(mockWalletMapper.transform(walletViewModelA)).thenReturn(walletA)
        whenever(mockWalletMapper.transform(walletViewModelB)).thenReturn(walletB)
        whenever(mockGetWalletSum.execute()).thenReturn(Maybe.empty())

        addWalletCount = 0
        deleteWalletCount = 0
        removeWalletCount = 0
        showContentCount = 0
        showErrorCount = 0
        showLoadingCount = 0
        showRefreshingCount = 0
        showRetryCount = 0
    }

    @After
    fun tearDown() {
        RxAndroidPlugins.reset()
        RxJavaPlugins.reset()
    }

    @Test
    fun testAttach() {
        val view = object : StubHomeView() {
        }

        val presenter = make()
        presenter.view = view

        verify(mockWalletAddWatcher).observe()
        verify(mockWalletChangeWatcher).observe()
        verify(mockWalletDeleteWatcher).observe()
    }

    @Test
    fun testDetach() {
        val presenter = make()
        presenter.view = null
    }

    @Test
    fun testAddWatcher() {
        mockWalletAddWatcher = mock(WalletAddWatcher::class.java)
        val view = object : StubHomeView() {
            override fun showContent() {
                showContentCount++
            }

            override fun addWallet(wallet: WalletViewModel) {
                assertThat(wallet).isEqualTo(walletViewModelA)
                addWalletCount++
            }
        }

        whenever(mockWalletAddWatcher.observe()).thenReturn(Observable.just(walletA))

        val presenter = make()
        presenter.view = view

        assertThat(showContentCount).isEqualTo(2)
        assertThat(addWalletCount).isEqualTo(1)
    }

    @Test
    fun testAddWatcherError() {
        mockWalletAddWatcher = mock(WalletAddWatcher::class.java)
        val view = object : StubHomeView() {
        }

        whenever(mockWalletAddWatcher.observe()).thenReturn(Observable.error(mockErrorA))

        val presenter = make()
        presenter.view = view

    }

    @Test
    fun testChangeWatcher() {
        mockWalletChangeWatcher = mock(WalletChangeWatcher::class.java)
        val view = object : StubHomeView() {
            override fun showContent() {
                showContentCount++
            }

            override fun addWallet(wallet: WalletViewModel) {
                assertThat(wallet).isEqualTo(walletViewModelA)
                addWalletCount++
            }
        }

        whenever(mockWalletChangeWatcher.observe()).thenReturn(Observable.just(walletA))

        val presenter = make()
        presenter.view = view

        assertThat(showContentCount).isEqualTo(2)
        assertThat(addWalletCount).isEqualTo(1)
    }

    @Test
    fun testChangeWatcherError() {
        mockWalletChangeWatcher = mock(WalletChangeWatcher::class.java)
        val view = object : StubHomeView() {
        }

        whenever(mockWalletChangeWatcher.observe()).thenReturn(Observable.error(mockErrorA))

        val presenter = make()
        presenter.view = view

    }

    @Test
    fun testDeleteWatcher() {
        mockWalletDeleteWatcher = mock(WalletDeleteWatcher::class.java)
        val view = object : StubHomeView() {
        }

        whenever(mockWalletDeleteWatcher.observe()).thenReturn(Observable.just(WalletFixture.id))

        val presenter = make()
        presenter.view = view
    }

    @Test
    fun testDeleteWatcherError() {
        mockWalletDeleteWatcher = mock(WalletDeleteWatcher::class.java)
        val view = object : StubHomeView() {
        }

        whenever(mockWalletDeleteWatcher.observe()).thenReturn(Observable.error(mockErrorA))

        val presenter = make()
        presenter.view = view

    }

    @Test
    fun testDeleteWatcherAfterLoad() {
        mockWalletDeleteWatcher = mock(WalletDeleteWatcher::class.java)
        val view = object : StubHomeView() {
            override fun deleteWallet(wallet: WalletViewModel) {
                assertThat(wallet).isEqualTo(walletViewModelA)
                deleteWalletCount++
            }
        }

        whenever(mockGetWallets.execute()).thenReturn(Observable.just(walletA))
        whenever(mockGetWalletsCount.execute()).thenReturn(Single.just(1))
        whenever(mockWalletDeleteWatcher.observe()).thenReturn(Observable.just(WalletFixture.id))

        val presenter = make()
        presenter.loadData()
        presenter.view = view

        assertThat(deleteWalletCount).isEqualTo(1)
    }

    @Test
    fun testLoadDataError() {
        val view = object : StubHomeView() {
            override fun showLoading() {
                showLoadingCount++
            }

            override fun showError(error: Throwable) {
                assertThat(error).isEqualTo(mockHumanReadableErrorA)
                showErrorCount++
            }

            override fun showRetry() {
                showRetryCount++
            }
        }

        whenever(mockGetWallets.execute()).thenReturn(Observable.error(mockErrorA))
        whenever(mockGetWalletsCount.execute()).thenReturn(Single.error(mockErrorB))

        val presenter = make()
        presenter.view = view
        presenter.loadData()

        assertThat(showErrorCount).isEqualTo(1)
        assertThat(showLoadingCount).isEqualTo(1)
        assertThat(showRetryCount).isEqualTo(1)
    }

    @Test
    fun testLoadDataEmpty() {
        val view = object : StubHomeView() {
            override fun showLoading() {
                showLoadingCount++
            }

            override fun showContent() {
                showContentCount++
            }
        }

        whenever(mockGetWallets.execute()).thenReturn(Observable.empty())
        whenever(mockGetWalletsCount.execute()).thenReturn(Single.just(0))

        val presenter = make()
        presenter.view = view
        presenter.loadData()

        assertThat(showContentCount).isEqualTo(1)
        assertThat(showLoadingCount).isEqualTo(1)
    }

    @Test
    fun testLoadDataSingle() {
        val view = object : StubHomeView() {
            override fun showLoading() {
                showLoadingCount++
            }

            override fun showContent() {
                showContentCount++
            }

            override fun addWallet(wallet: WalletViewModel) {
                assertThat(wallet).isEqualTo(walletViewModelA)
                addWalletCount++
            }
        }

        whenever(mockGetWallets.execute()).thenReturn(Observable.just(walletA))
        whenever(mockGetWalletsCount.execute()).thenReturn(Single.just(1))

        val presenter = make()
        presenter.view = view
        presenter.loadData()

        assertThat(addWalletCount).isEqualTo(1)
        assertThat(showContentCount).isEqualTo(2)
        assertThat(showLoadingCount).isEqualTo(1)
    }

    @Test
    fun testLoadDataMultiple() {
        val view = object : StubHomeView() {
            override fun showLoading() {
                showLoadingCount++
            }

            override fun showContent() {
                showContentCount++
            }

            override fun addWallet(wallet: WalletViewModel) {
                assertThat(wallet).isIn(walletViewModelA, walletViewModelB)
                addWalletCount++
            }
        }

        whenever(mockGetWallets.execute()).thenReturn(Observable.just(walletA, walletB))
        whenever(mockGetWalletsCount.execute()).thenReturn(Single.just(2))

        val presenter = make()
        presenter.view = view
        presenter.loadData()

        assertThat(addWalletCount).isEqualTo(2)
        assertThat(showContentCount).isEqualTo(3)
        assertThat(showLoadingCount).isEqualTo(1)
    }

    @Test
    fun testLoadDataSuccessAndSuccess() {
        val view = object : StubHomeView() {
            override fun showLoading() {
                showLoadingCount++
            }

            override fun showContent() {
                showContentCount++
            }

            override fun addWallet(wallet: WalletViewModel) {
                assertThat(wallet).isIn(walletViewModelA, walletViewModelB)
                addWalletCount++
            }

            override fun removeWallet(wallet: WalletViewModel) {
                assertThat(wallet).isIn(walletViewModelA, walletViewModelB)
                removeWalletCount++
            }
        }

        whenever(mockGetWallets.execute()).thenReturn(Observable.just(walletA, walletB))
        whenever(mockGetWalletsCount.execute()).thenReturn(Single.just(2))

        val presenter = make()
        presenter.view = view
        presenter.loadData()
        presenter.loadData()

        assertThat(addWalletCount).isEqualTo(4)
        assertThat(removeWalletCount).isEqualTo(2)
        assertThat(showContentCount).isEqualTo(6)
        assertThat(showLoadingCount).isEqualTo(2)
    }

    @Test
    fun testLoadDataSuccessAndError() {
        val view = object : StubHomeView() {
            override fun showLoading() {
                showLoadingCount++
            }

            override fun showContent() {
                showContentCount++
            }

            override fun showError(error: Throwable) {
                assertThat(error).isEqualTo(mockHumanReadableErrorA)
                showErrorCount++
            }

            override fun addWallet(wallet: WalletViewModel) {
                assertThat(wallet).isIn(walletViewModelA, walletViewModelB)
                addWalletCount++
            }

            override fun removeWallet(wallet: WalletViewModel) {
                assertThat(wallet).isIn(walletViewModelA, walletViewModelB)
                removeWalletCount++
            }
        }

        whenever(mockGetWallets.execute()).thenReturn(Observable.just(walletA, walletB), Observable.error(mockErrorA))
        whenever(mockGetWalletsCount.execute()).thenReturn(Single.just(2), Single.error(mockErrorB))

        val presenter = make()
        presenter.view = view
        presenter.loadData()
        presenter.loadData()

        assertThat(addWalletCount).isEqualTo(2)
        assertThat(showContentCount).isEqualTo(4)
        assertThat(showErrorCount).isEqualTo(1)
        assertThat(showLoadingCount).isEqualTo(2)
    }

    @Test
    fun testLoadDataErrorAndSuccess() {
        val view = object : StubHomeView() {
            override fun showLoading() {
                showLoadingCount++
            }

            override fun showContent() {
                showContentCount++
            }

            override fun showRetry() {
                showRetryCount++
            }

            override fun showError(error: Throwable) {
                assertThat(error).isEqualTo(mockHumanReadableErrorA)
                showErrorCount++
            }

            override fun addWallet(wallet: WalletViewModel) {
                assertThat(wallet).isIn(walletViewModelA, walletViewModelB)
                addWalletCount++
            }

            override fun removeWallet(wallet: WalletViewModel) {
                assertThat(wallet).isIn(walletViewModelA, walletViewModelB)
                removeWalletCount++
            }
        }

        whenever(mockGetWallets.execute()).thenReturn(Observable.error(mockErrorA), Observable.just(walletA, walletB))
        whenever(mockGetWalletsCount.execute()).thenReturn(Single.error(mockErrorB), Single.just(2))

        val presenter = make()
        presenter.view = view
        presenter.loadData()
        presenter.loadData()

        assertThat(addWalletCount).isEqualTo(2)
        assertThat(showRetryCount).isEqualTo(1)
        assertThat(showContentCount).isEqualTo(3)
        assertThat(showErrorCount).isEqualTo(1)
        assertThat(showLoadingCount).isEqualTo(2)
    }

    @Test
    fun testLoadDataErrorAndError() {
        val view = object : StubHomeView() {
            override fun showLoading() {
                showLoadingCount++
            }

            override fun showError(error: Throwable) {
                assertThat(error).isEqualTo(mockHumanReadableErrorA)
                showErrorCount++
            }

            override fun showRetry() {
                showRetryCount++
            }
        }

        whenever(mockGetWallets.execute()).thenReturn(Observable.error(mockErrorA), Observable.error(mockErrorA))
        whenever(mockGetWalletsCount.execute()).thenReturn(Single.error(mockErrorB), Single.error(mockErrorB))

        val presenter = make()
        presenter.view = view
        presenter.loadData()
        presenter.loadData()

        assertThat(showErrorCount).isEqualTo(2)
        assertThat(showLoadingCount).isEqualTo(2)
        assertThat(showRetryCount).isEqualTo(2)
    }

    @Test
    fun testReloadError() {
        val view = object : StubHomeView() {
            override fun showLoading() {
                showLoadingCount++
            }

            override fun showError(error: Throwable) {
                assertThat(error).isEqualTo(mockHumanReadableErrorA)
                showErrorCount++
            }

            override fun showRetry() {
                showRetryCount++
            }
        }

        whenever(mockUpdateWallets.execute()).thenReturn(Observable.error(mockErrorA))

        val presenter = make()
        presenter.view = view
        presenter.reload()

        assertThat(showErrorCount).isEqualTo(1)
        assertThat(showLoadingCount).isEqualTo(1)
        assertThat(showRetryCount).isEqualTo(1)
    }

    @Test
    fun testReloadEmpty() {
        val view = object : StubHomeView() {
            override fun showLoading() {
                showLoadingCount++
            }

            override fun showContent() {
                showContentCount++
            }
        }

        whenever(mockUpdateWallets.execute()).thenReturn(Observable.empty())

        val presenter = make()
        presenter.view = view
        presenter.reload()

        assertThat(showContentCount).isEqualTo(1)
        assertThat(showLoadingCount).isEqualTo(1)
    }

    @Test
    fun testReloadSingle() {
        val view = object : StubHomeView() {
            override fun showLoading() {
                showLoadingCount++
            }

            override fun showContent() {
                showContentCount++
            }

            override fun addWallet(wallet: WalletViewModel) {
                assertThat(wallet).isEqualTo(walletViewModelA)
                addWalletCount++
            }
        }

        whenever(mockUpdateWallets.execute()).thenReturn(Observable.just(walletA))

        val presenter = make()
        presenter.view = view
        presenter.reload()

        assertThat(addWalletCount).isEqualTo(1)
        assertThat(showContentCount).isEqualTo(2)
        assertThat(showLoadingCount).isEqualTo(1)
    }

    @Test
    fun testReloadMultiple() {
        val view = object : StubHomeView() {
            override fun showLoading() {
                showLoadingCount++
            }

            override fun showContent() {
                showContentCount++
            }

            override fun addWallet(wallet: WalletViewModel) {
                assertThat(wallet).isIn(walletViewModelA, walletViewModelB)
                addWalletCount++
            }
        }

        whenever(mockUpdateWallets.execute()).thenReturn(Observable.just(walletA, walletB))

        val presenter = make()
        presenter.view = view
        presenter.reload()

        assertThat(addWalletCount).isEqualTo(2)
        assertThat(showContentCount).isEqualTo(3)
        assertThat(showLoadingCount).isEqualTo(1)
    }

    @Test
    fun testReloadSuccessAndSuccess() {
        val view = object : StubHomeView() {
            override fun showLoading() {
                showLoadingCount++
            }

            override fun showContent() {
                showContentCount++
            }

            override fun addWallet(wallet: WalletViewModel) {
                assertThat(wallet).isIn(walletViewModelA, walletViewModelB)
                addWalletCount++
            }

            override fun removeWallet(wallet: WalletViewModel) {
                assertThat(wallet).isIn(walletViewModelA, walletViewModelB)
                removeWalletCount++
            }
        }

        whenever(mockUpdateWallets.execute()).thenReturn(Observable.just(walletA, walletB))

        val presenter = make()
        presenter.view = view
        presenter.reload()
        presenter.reload()

        assertThat(addWalletCount).isEqualTo(4)
        assertThat(removeWalletCount).isEqualTo(2)
        assertThat(showContentCount).isEqualTo(6)
        assertThat(showLoadingCount).isEqualTo(2)
    }

    @Test
    fun testReloadSuccessAndError() {
        val view = object : StubHomeView() {
            override fun showLoading() {
                showLoadingCount++
            }

            override fun showContent() {
                showContentCount++
            }

            override fun showError(error: Throwable) {
                assertThat(error).isEqualTo(mockHumanReadableErrorA)
                showErrorCount++
            }

            override fun addWallet(wallet: WalletViewModel) {
                assertThat(wallet).isIn(walletViewModelA, walletViewModelB)
                addWalletCount++
            }

            override fun removeWallet(wallet: WalletViewModel) {
                assertThat(wallet).isIn(walletViewModelA, walletViewModelB)
                removeWalletCount++
            }
        }

        whenever(mockUpdateWallets.execute()).thenReturn(Observable.just(walletA, walletB), Observable.error(mockErrorA))

        val presenter = make()
        presenter.view = view
        presenter.reload()
        presenter.reload()

        assertThat(addWalletCount).isEqualTo(2)
        assertThat(showContentCount).isEqualTo(4)
        assertThat(showErrorCount).isEqualTo(1)
        assertThat(showLoadingCount).isEqualTo(2)
    }

    @Test
    fun testReloadErrorAndSuccess() {
        val view = object : StubHomeView() {
            override fun showLoading() {
                showLoadingCount++
            }

            override fun showContent() {
                showContentCount++
            }

            override fun showRetry() {
                showRetryCount++
            }

            override fun showError(error: Throwable) {
                assertThat(error).isEqualTo(mockHumanReadableErrorA)
                showErrorCount++
            }

            override fun addWallet(wallet: WalletViewModel) {
                assertThat(wallet).isIn(walletViewModelA, walletViewModelB)
                addWalletCount++
            }

            override fun removeWallet(wallet: WalletViewModel) {
                assertThat(wallet).isIn(walletViewModelA, walletViewModelB)
                removeWalletCount++
            }
        }

        whenever(mockUpdateWallets.execute()).thenReturn(Observable.error(mockErrorA), Observable.just(walletA, walletB))

        val presenter = make()
        presenter.view = view
        presenter.reload()
        presenter.reload()

        assertThat(addWalletCount).isEqualTo(2)
        assertThat(showRetryCount).isEqualTo(1)
        assertThat(showContentCount).isEqualTo(3)
        assertThat(showErrorCount).isEqualTo(1)
        assertThat(showLoadingCount).isEqualTo(2)
    }

    @Test
    fun testReloadErrorAndError() {
        val view = object : StubHomeView() {
            override fun showLoading() {
                showLoadingCount++
            }

            override fun showError(error: Throwable) {
                assertThat(error).isEqualTo(mockHumanReadableErrorA)
                showErrorCount++
            }

            override fun showRetry() {
                showRetryCount++
            }
        }

        whenever(mockUpdateWallets.execute()).thenReturn(Observable.error(mockErrorA), Observable.error(mockErrorA))

        val presenter = make()
        presenter.view = view
        presenter.reload()
        presenter.reload()

        assertThat(showErrorCount).isEqualTo(2)
        assertThat(showLoadingCount).isEqualTo(2)
        assertThat(showRetryCount).isEqualTo(2)
    }

    @Test
    fun testRefreshError() {
        val view = object : StubHomeView() {
            override fun showRefreshing() {
                showRefreshingCount++
            }

            override fun showError(error: Throwable) {
                assertThat(error).isEqualTo(mockHumanReadableErrorA)
                showErrorCount++
            }

            override fun showRetry() {
                showRetryCount++
            }
        }

        whenever(mockUpdateWallets.execute()).thenReturn(Observable.error(mockErrorA))

        val presenter = make()
        presenter.view = view
        presenter.refresh()

        assertThat(showErrorCount).isEqualTo(1)
        assertThat(showRefreshingCount).isEqualTo(1)
        assertThat(showRetryCount).isEqualTo(1)
    }

    @Test
    fun testRefreshEmpty() {
        val view = object : StubHomeView() {
            override fun showRefreshing() {
                showRefreshingCount++
            }

            override fun showContent() {
                showContentCount++
            }
        }

        whenever(mockUpdateWallets.execute()).thenReturn(Observable.empty())

        val presenter = make()
        presenter.view = view
        presenter.refresh()

        assertThat(showContentCount).isEqualTo(1)
        assertThat(showRefreshingCount).isEqualTo(1)
    }

    @Test
    fun testRefreshSingle() {
        val view = object : StubHomeView() {
            override fun showRefreshing() {
                showRefreshingCount++
            }

            override fun showContent() {
                showContentCount++
            }

            override fun addWallet(wallet: WalletViewModel) {
                assertThat(wallet).isEqualTo(walletViewModelA)
                addWalletCount++
            }
        }

        whenever(mockUpdateWallets.execute()).thenReturn(Observable.just(walletA))

        val presenter = make()
        presenter.view = view
        presenter.refresh()

        assertThat(addWalletCount).isEqualTo(1)
        assertThat(showContentCount).isEqualTo(2)
        assertThat(showRefreshingCount).isEqualTo(1)
    }

    @Test
    fun testRefreshMultiple() {
        val view = object : StubHomeView() {
            override fun showRefreshing() {
                showRefreshingCount++
            }

            override fun showContent() {
                showContentCount++
            }

            override fun addWallet(wallet: WalletViewModel) {
                assertThat(wallet).isIn(walletViewModelA, walletViewModelB)
                addWalletCount++
            }
        }

        whenever(mockUpdateWallets.execute()).thenReturn(Observable.just(walletA, walletB))

        val presenter = make()
        presenter.view = view
        presenter.refresh()

        assertThat(addWalletCount).isEqualTo(2)
        assertThat(showContentCount).isEqualTo(3)
        assertThat(showRefreshingCount).isEqualTo(1)
    }

    @Test
    fun testRefreshSuccessAndSuccess() {
        val view = object : StubHomeView() {
            override fun showRefreshing() {
                showRefreshingCount++
            }

            override fun showContent() {
                showContentCount++
            }

            override fun addWallet(wallet: WalletViewModel) {
                assertThat(wallet).isIn(walletViewModelA, walletViewModelB)
                addWalletCount++
            }

            override fun removeWallet(wallet: WalletViewModel) {
                assertThat(wallet).isIn(walletViewModelA, walletViewModelB)
                removeWalletCount++
            }
        }

        whenever(mockUpdateWallets.execute()).thenReturn(Observable.just(walletA, walletB))

        val presenter = make()
        presenter.view = view
        presenter.refresh()
        presenter.refresh()

        assertThat(addWalletCount).isEqualTo(4)
        assertThat(removeWalletCount).isEqualTo(2)
        assertThat(showContentCount).isEqualTo(6)
        assertThat(showRefreshingCount).isEqualTo(2)
    }

    @Test
    fun testRefreshSuccessAndError() {
        val view = object : StubHomeView() {
            override fun showRefreshing() {
                showRefreshingCount++
            }

            override fun showContent() {
                showContentCount++
            }

            override fun showError(error: Throwable) {
                assertThat(error).isEqualTo(mockHumanReadableErrorA)
                showErrorCount++
            }

            override fun addWallet(wallet: WalletViewModel) {
                assertThat(wallet).isIn(walletViewModelA, walletViewModelB)
                addWalletCount++
            }

            override fun removeWallet(wallet: WalletViewModel) {
                assertThat(wallet).isIn(walletViewModelA, walletViewModelB)
                removeWalletCount++
            }
        }

        whenever(mockUpdateWallets.execute()).thenReturn(Observable.just(walletA, walletB), Observable.error(mockErrorA))

        val presenter = make()
        presenter.view = view
        presenter.refresh()
        presenter.refresh()

        assertThat(addWalletCount).isEqualTo(2)
        assertThat(showContentCount).isEqualTo(4)
        assertThat(showErrorCount).isEqualTo(1)
        assertThat(showRefreshingCount).isEqualTo(2)
    }

    @Test
    fun testRefreshErrorAndSuccess() {
        val view = object : StubHomeView() {
            override fun showRefreshing() {
                showRefreshingCount++
            }

            override fun showContent() {
                showContentCount++
            }

            override fun showRetry() {
                showRetryCount++
            }

            override fun showError(error: Throwable) {
                assertThat(error).isEqualTo(mockHumanReadableErrorA)
                showErrorCount++
            }

            override fun addWallet(wallet: WalletViewModel) {
                assertThat(wallet).isIn(walletViewModelA, walletViewModelB)
                addWalletCount++
            }

            override fun removeWallet(wallet: WalletViewModel) {
                assertThat(wallet).isIn(walletViewModelA, walletViewModelB)
                removeWalletCount++
            }
        }

        whenever(mockUpdateWallets.execute()).thenReturn(Observable.error(mockErrorA), Observable.just(walletA, walletB))

        val presenter = make()
        presenter.view = view
        presenter.refresh()
        presenter.refresh()

        assertThat(addWalletCount).isEqualTo(2)
        assertThat(showRetryCount).isEqualTo(1)
        assertThat(showContentCount).isEqualTo(3)
        assertThat(showErrorCount).isEqualTo(1)
        assertThat(showRefreshingCount).isEqualTo(2)
    }

    @Test
    fun testRefreshErrorAndError() {
        val view = object : StubHomeView() {
            override fun showRefreshing() {
                showRefreshingCount++
            }

            override fun showError(error: Throwable) {
                assertThat(error).isEqualTo(mockHumanReadableErrorA)
                showErrorCount++
            }

            override fun showRetry() {
                showRetryCount++
            }
        }

        whenever(mockUpdateWallets.execute()).thenReturn(Observable.error(mockErrorA), Observable.error(mockErrorA))

        val presenter = make()
        presenter.view = view
        presenter.refresh()
        presenter.refresh()

        assertThat(showErrorCount).isEqualTo(2)
        assertThat(showRefreshingCount).isEqualTo(2)
        assertThat(showRetryCount).isEqualTo(2)
    }

    @Test
    fun testAbout() {
        val presenter = make()
        presenter.about()

        verify(mockNavigator).launchAbout()
    }

    @Test
    fun testCheckAddress() {
        val presenter = make()
        presenter.checkAddress()

        verify(mockNavigator).launchCheckAddress()
    }

    @Test
    fun testUndoDeleteSuccess() {
        whenever(mockAddWallet.execute(walletA)).thenReturn(Completable.complete())

        val presenter = make()
        presenter.undoDelete(walletViewModelA)

        verify(mockAddWallet).execute(walletA)
    }

    @Test
    fun testUndoDeleteError() {
        whenever(mockAddWallet.execute(walletA)).thenReturn(Completable.error(mockErrorA))

        val presenter = make()
        presenter.undoDelete(walletViewModelA)

    }

    @Test
    fun testSettings() {
        val presenter = make()
        presenter.settings()

        verify(mockNavigator).launchSettings()
    }

    private fun make() = HomePresenter(
            mockGetWallets,
            mockGetWalletSum,
            mockUpdateWallets,
            mockWalletMapper,
            mockAddWallet,
            mockGetWalletsCount,
            mockNavigator,
            mockWalletAddWatcher,
            mockWalletChangeWatcher,
            mockWalletDeleteWatcher,
            mockStandardErrors
    )

}
