package dev.akashkamble.holdingsdemo.ui.holdings

import android.util.Log
import app.cash.turbine.test
import dev.akashkamble.holdingsdemo.domain.model.Holding
import dev.akashkamble.holdingsdemo.domain.result.Result
import dev.akashkamble.holdingsdemo.fake.FakeHoldingsRepo
import dev.akashkamble.holdingsdemo.ui.holdings.viewmodel.HoldingsViewModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.spyk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HoldingsViewModelTest {
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: HoldingsViewModel
    private lateinit var repo: FakeHoldingsRepo

    private val holdings = listOf(
        Holding("TCS", 10, 150.0, 140.0, 145.0),
        Holding("ICICI", 5, 2800.0, 2700.0, 2750.0)
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        mockkStatic(Log::class)
        every { Log.d(any(), any(), any()) } returns 0
        repo = spyk(FakeHoldingsRepo())
    }

    @After
    fun tearDown() {
        // Reset main dispatcher to the original Main dispatcher
        Dispatchers.resetMain()
    }

    @Test
    fun `init should load holdings successfully`() = runTest {
        repo.refreshResult = Result.Success(Unit)
        repo.emitHoldings(holdings)
        viewModel = HoldingsViewModel(repo, testDispatcher)

        viewModel.uiState.test {

            val loading = awaitItem()
            assertTrue(loading.isLoading)

            val loaded = awaitItem()
            assertEquals(0, loaded.data.holdings.items.size)

            val state = awaitItem()
            assertFalse(state.isLoading)
            assertNull(state.error)
            assertEquals(2, state.data.holdings.items.size)
        }
    }

    @Test
    fun `init should expose error when refresh fails`() = runTest {
        repo.refreshResult = Result.Error("Network Error")
        viewModel = HoldingsViewModel(repo, testDispatcher)

        viewModel.uiState.test {
            val initial = awaitItem()
            assertTrue(initial.isLoading)

            val errorState = awaitItem()
            assertFalse(errorState.isLoading)
            assertEquals("Network Error", errorState.error)
        }
    }

    @Test
    fun `retry should trigger loading again`() = runTest {
        coEvery { repo.refreshHoldings() } returnsMany listOf(
            Result.Error("Network Error"),
            Result.Success(Unit)
        )
        every { repo.observeHoldings() } returns flowOf(emptyList())

        viewModel = HoldingsViewModel(repo, testDispatcher)

        viewModel.uiState.test {

            awaitItem() // loading
            awaitItem() // error

            viewModel.handleActions(HoldingsScreenAction.RetryEvent)

            val retryLoading = awaitItem()
            assertTrue(retryLoading.isLoading)
            assertNull(retryLoading.error)
        }
    }

    @Test
    fun `observeHoldings should update ui when db emits`() = runTest {

        coEvery { repo.refreshHoldings() } returns Result.Success(Unit)

        viewModel = HoldingsViewModel(repo, testDispatcher)

        viewModel.uiState.test {
            awaitItem() // loading
            awaitItem() // API Success

            repo.emitHoldings(
                listOf(
                    Holding("TCS", 10, 150.0, 140.0, 145.0),
                    Holding("ICICI", 5, 2800.0, 2700.0, 2750.0),
                    Holding("SBI", 5, 2800.0, 2700.0, 2750.0),
                    Holding("HDFC", 5, 2800.0, 2700.0, 2750.0)
                )
            )
            val updated = awaitItem()
            assertEquals(4, updated.data.holdings.items.size)
        }
    }

    @Test
    fun `toggle summary should update isSummaryExpanded`() = runTest {
        coEvery { repo.refreshHoldings() } returns Result.Success(Unit)
        every { repo.observeHoldings() } returns flowOf(emptyList())

        viewModel = HoldingsViewModel(repo, testDispatcher)

        viewModel.uiState.test {

            val initial = awaitItem()
            assertFalse(initial.data.isSummaryExpanded)

            awaitItem() // API Success

            viewModel.handleActions(HoldingsScreenAction.ToggleSummaryEvent)

            val toggled = awaitItem()
            assertTrue(toggled.data.isSummaryExpanded)
        }
    }
}