package dev.akashkamble.holdingsdemo.ui.holdings

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
import io.mockk.spyk
import junit.framework.TestCase.assertEquals
import org.junit.Assert.assertFalse
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalCoroutinesApi::class)
class HoldingsViewModelTest {


    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: HoldingsViewModel
    private lateinit var repo: FakeHoldingsRepo

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
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
            val loadingState = awaitItem()
            testDispatcher.scheduler.advanceUntilIdle()
            val loadState = awaitItem()
            assertTrue(loadingState.isLoading)
            assertFalse(loadState.isLoading)
            assertEquals(3, loadState.holdings.size)
        }
    }

    @Test
    fun `should fetch holdings on init and update uistate with error`() = runTest {
        // Given
        coEvery {
            repo.getHoldings()
        } returns Result.Error("Network Error")

        viewModel = HoldingsViewModel(
            repo = repo,
            ioDispatcher = testDispatcher
        )

        viewModel.uiState.test {
            val loadingState = awaitItem()
            testDispatcher.scheduler.advanceUntilIdle()
            val loadState = awaitItem()
            assertTrue(loadingState.isLoading)
            assertFalse(loadState.isLoading)
            assertEquals(0, loadState.holdings.size)
            assertEquals("Network Error", loadState.error)
        }
    }


}