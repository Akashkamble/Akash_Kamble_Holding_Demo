package dev.akashkamble.holdingsdemo.ui.holdings

import android.util.Log
import dev.akashkamble.holdingsdemo.fake.FakeHoldingsRepo
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import app.cash.turbine.test
import dev.akashkamble.holdingsdemo.domain.result.Result
import dev.akashkamble.holdingsdemo.ui.holdings.viewmodel.HoldingsViewModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.spyk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flow
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull

@OptIn(ExperimentalCoroutinesApi::class)
class HoldingsViewModelTest {


    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: HoldingsViewModel
    private lateinit var repo: FakeHoldingsRepo

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        mockkStatic(Log::class)
        every { Log.d(any(), any(), any()) } returns 0
        repo = spyk(FakeHoldingsRepo())
        viewModel = HoldingsViewModel(repo = repo, ioDispatcher = testDispatcher)
    }

    @After
    fun tearDown() {
        // Reset main dispatcher to the original Main dispatcher
        Dispatchers.resetMain()
    }

    @Test
    fun `should fetch holdings on init and update uistate with loading`() = runTest {
        // Given
        viewModel = HoldingsViewModel(
            repo = repo,
            ioDispatcher = testDispatcher
        )

        viewModel.uiState.test {
            // Loading emission
            val loadingState = awaitItem()
            testDispatcher.scheduler.advanceUntilIdle()

            // Data emission
            val loadState = awaitItem()
            assertTrue(loadingState.isLoading)
            assertFalse(loadState.isLoading)
            assertEquals(3, loadState.data.holdings.size)
        }
    }

    @Test
    fun `should fetch holdings on init and update uistate with error`() = runTest {

        coEvery { repo.refreshHoldings() } returns Result.Error("Network Error")
        every { repo.observeHoldings() } returns flow { emit(emptyList()) }

        viewModel = HoldingsViewModel(
            repo = repo,
            ioDispatcher = testDispatcher
        )

        viewModel.uiState.test {

            // Loading emission
            val loading = awaitItem()
            assertTrue(loading.isLoading)

            // DB emission
            val db = awaitItem()
            assertFalse(db.isLoading)
            assertEquals(0, db.data.holdings.size)
            assertNull(db.error)

            // Error emission
            val error = awaitItem()
            assertFalse(error.isLoading)
            assertEquals("Network Error", error.error)
        }
    }



}