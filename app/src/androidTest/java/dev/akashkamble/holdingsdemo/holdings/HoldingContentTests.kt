package dev.akashkamble.holdingsdemo.holdings

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import dev.akashkamble.holdingsdemo.ComposeTest
import dev.akashkamble.holdingsdemo.domain.model.Holding
import dev.akashkamble.holdingsdemo.ui.holdings.HoldingsScreenAction
import dev.akashkamble.holdingsdemo.ui.holdings.composables.HoldingsContent
import dev.akashkamble.holdingsdemo.ui.model.HoldingData
import dev.akashkamble.holdingsdemo.ui.model.HoldingsUiState
import dev.akashkamble.holdingsdemo.ui.model.ImmutableList
import dev.akashkamble.holdingsdemo.ui.theme.HoldingsTheme
import org.junit.Test

class HoldingContentTests: ComposeTest() {

    private lateinit var state: HoldingsUiState

    @Test
    fun holdingContent_showsLoading() {
        state = HoldingsUiState(
            isLoading = true
        )

        composeTestRule.setContent {
            HoldingsTheme {
                HoldingsContent(state) { }
            }
        }
        holdingContentRobot(composeTestRule) {
            assertLoaderIsDisplayed()
            assertErrorIsNotDisplayed()
        }
    }

    @Test
    fun holdingContent_hidesLoading() {
        state = HoldingsUiState(
            isLoading = false
        )

        composeTestRule.setContent {
            HoldingsTheme {
                HoldingsContent(state) { }
            }
        }
        holdingContentRobot(composeTestRule) {
            assertLoaderIsNotDisplayed()
            assertErrorIsNotDisplayed()
        }
    }

    @Test
    fun holdingContent_hidesLoading_whenErrorState() {
        val errorMessage = "Some Error"
        state = HoldingsUiState(
            isLoading = false,
            error = errorMessage
        )

        composeTestRule.setContent {
            HoldingsTheme {
                HoldingsContent(state) { }
            }
        }
        holdingContentRobot(composeTestRule) {
            assertLoaderIsNotDisplayed()
            assertErrorIsDisplayed(errorMessage)
        }
    }

    @Test
    fun holdingContent_retryEmitsCorrectActionEvent() {
        val errorMessage = "Some Error"
        var action: HoldingsScreenAction = HoldingsScreenAction.ToggleSummaryEvent
        state = HoldingsUiState(
            isLoading = false,
            error = errorMessage
        )

        composeTestRule.setContent {
            HoldingsTheme {
                HoldingsContent(state) {
                    action = it
                }
            }
        }
        holdingContentRobot(composeTestRule) {
            assertLoaderIsNotDisplayed()
            assertErrorIsDisplayed(errorMessage)
            clickRetry()
            assert(action is HoldingsScreenAction.RetryEvent)
        }
    }

    @Test
    fun holdingContent_displaysHoldingDataWithCollapsedSummary() {

        state = HoldingsUiState(
            isLoading = false,
            data = HoldingData(
                holdings = ImmutableList(
                    items = listOf(
                        Holding(
                            symbol = "SBI",
                            quantity = 10,
                            ltp = 150.0,
                            avgPrice = 140.0,
                            closePrice = 145.0
                        ),
                        Holding(
                            symbol = "ICICI",
                            quantity = 5,
                            ltp = 2800.0,
                            avgPrice = 2700.0,
                            closePrice = 2750.0
                        )
                    )
                )
            )
        )

        composeTestRule.setContent {
            HoldingsTheme {
                HoldingsContent(state) { }
            }
        }
        holdingContentRobot(composeTestRule) {
            assertLoaderIsNotDisplayed()
            assertErrorIsNotDisplayed()

            // Verify for first holding
            symbolIsDisplayed("SBI")
            ltpLabelForSymbolIsDisplayed("SBI")
            lptValueForSymbolIsDisplayed("SBI")
            netQtyLabelForSymbolIsDisplayed("SBI")
            netQtyValueForSymbolIsDisplayed("SBI")
            pnlLabelForSymbolIsDisplayed("SBI")
            pnlValueForSymbolIsDisplayed("SBI")

            // Verify for second holding
            symbolIsDisplayed("ICICI")
            ltpLabelForSymbolIsDisplayed("ICICI")
            lptValueForSymbolIsDisplayed("ICICI")
            netQtyLabelForSymbolIsDisplayed("ICICI")
            netQtyValueForSymbolIsDisplayed("ICICI")
            pnlLabelForSymbolIsDisplayed("ICICI")
            pnlValueForSymbolIsDisplayed("ICICI")

            totalPnlIsDisplayed()
            totalPnlValueIsDisplayed()
            summaryArrowIsDisplayed()
            summaryLabelForLabelIsNotDisplayed("Current Value")
            summaryValueForLabelIsNotDisplayed("Current Value")
        }
    }

    @Test
    fun holdingContent_displaysHoldingDataWithExpandedSummary() {

        state = HoldingsUiState(
            isLoading = false,
            data = HoldingData(
                holdings = ImmutableList(
                    items = listOf(
                        Holding(
                            symbol = "SBI",
                            quantity = 10,
                            ltp = 150.0,
                            avgPrice = 140.0,
                            closePrice = 145.0
                        ),
                        Holding(
                            symbol = "ICICI",
                            quantity = 5,
                            ltp = 2800.0,
                            avgPrice = 2700.0,
                            closePrice = 2750.0
                        )
                    )
                ),
                isSummaryExpanded = true
            )
        )

        composeTestRule.setContent {
            HoldingsTheme {
                HoldingsContent(state) { }
            }
        }
        holdingContentRobot(composeTestRule) {
            assertLoaderIsNotDisplayed()
            assertErrorIsNotDisplayed()

            // Verify for first holding
            symbolIsDisplayed("SBI")
            ltpLabelForSymbolIsDisplayed("SBI")
            lptValueForSymbolIsDisplayed("SBI")
            netQtyLabelForSymbolIsDisplayed("SBI")
            netQtyValueForSymbolIsDisplayed("SBI")
            pnlLabelForSymbolIsDisplayed("SBI")
            pnlValueForSymbolIsDisplayed("SBI")

            // Verify for second holding
            symbolIsDisplayed("ICICI")
            ltpLabelForSymbolIsDisplayed("ICICI")
            lptValueForSymbolIsDisplayed("ICICI")
            netQtyLabelForSymbolIsDisplayed("ICICI")
            netQtyValueForSymbolIsDisplayed("ICICI")
            pnlLabelForSymbolIsDisplayed("ICICI")
            pnlValueForSymbolIsDisplayed("ICICI")

            totalPnlIsDisplayed()
            totalPnlValueIsDisplayed()
            summaryArrowIsDisplayed()
            summaryLabelForLabelIsDisplayed("Current value")
            summaryValueForLabelIsDisplayed("Current value")
            summaryLabelForLabelIsDisplayed("Total investment")
            summaryValueForLabelIsDisplayed("Total investment")
            summaryLabelForLabelIsDisplayed("Day’s P&L")
            summaryValueForLabelIsDisplayed("Day’s P&L")
        }
    }

    @Test
    fun holdingContent_clickingOnSummaryToggleEmitsCorrectActionEvent() {
        var action: HoldingsScreenAction = HoldingsScreenAction.RetryEvent
        state = HoldingsUiState(
            isLoading = false,
            data = HoldingData(
                holdings = ImmutableList(
                    items = listOf(
                        Holding(
                            symbol = "SBI",
                            quantity = 10,
                            ltp = 150.0,
                            avgPrice = 140.0,
                            closePrice = 145.0
                        ),
                        Holding(
                            symbol = "ICICI",
                            quantity = 5,
                            ltp = 2800.0,
                            avgPrice = 2700.0,
                            closePrice = 2750.0
                        )
                    )
                )
            )
        )

        composeTestRule.setContent {
            HoldingsTheme {
                HoldingsContent(state) {
                    action = it
                }
            }
        }
        holdingContentRobot(composeTestRule) {
            assertLoaderIsNotDisplayed()
            assertErrorIsNotDisplayed()

            // Verify for first holding
            symbolIsDisplayed("SBI")
            ltpLabelForSymbolIsDisplayed("SBI")
            lptValueForSymbolIsDisplayed("SBI")
            netQtyLabelForSymbolIsDisplayed("SBI")
            netQtyValueForSymbolIsDisplayed("SBI")
            pnlLabelForSymbolIsDisplayed("SBI")
            pnlValueForSymbolIsDisplayed("SBI")

            // Verify for second holding
            symbolIsDisplayed("ICICI")
            ltpLabelForSymbolIsDisplayed("ICICI")
            lptValueForSymbolIsDisplayed("ICICI")
            netQtyLabelForSymbolIsDisplayed("ICICI")
            netQtyValueForSymbolIsDisplayed("ICICI")
            pnlLabelForSymbolIsDisplayed("ICICI")
            pnlValueForSymbolIsDisplayed("ICICI")

            totalPnlIsDisplayed()
            totalPnlValueIsDisplayed()
            summaryArrowIsDisplayed()
            summaryLabelForLabelIsNotDisplayed("Current Value")
            summaryValueForLabelIsNotDisplayed("Current Value")

            clickSummeryToggle()
            assert(action is HoldingsScreenAction.ToggleSummaryEvent)
        }
    }
}

class HoldingContentRobot(
    val composeTestRule: ComposeTestRule
) {
    private val loader = composeTestRule.onNodeWithTag("loader")
    private val retryCta = composeTestRule.onNodeWithText("Retry")
    private val errorImage = composeTestRule.onNodeWithContentDescription("Error Icon")
    private val summaryArrow = composeTestRule.onNodeWithContentDescription("Expand/Collapse Holdings Summery", useUnmergedTree = true)

    fun assertLoaderIsDisplayed() {
        loader.assertIsDisplayed()
    }
    fun assertLoaderIsNotDisplayed() {
        loader.assertIsNotDisplayed()
    }
    fun assertErrorIsDisplayed(errorMessage: String) {
        retryCta.assertIsDisplayed()
        errorImage.assertIsDisplayed()
        composeTestRule.onNodeWithText(errorMessage).assertIsDisplayed()
    }
    fun assertErrorIsNotDisplayed() {
        retryCta.assertIsNotDisplayed()
        errorImage.assertIsNotDisplayed()
    }

    fun ltpLabelForSymbolIsDisplayed(symbol: String) {
        composeTestRule.onNodeWithTag("ltp_$symbol").assertIsDisplayed()
    }

    fun lptValueForSymbolIsDisplayed(symbol: String) {
        composeTestRule.onNodeWithTag("ltp_value_$symbol").assertIsDisplayed()
    }

    fun netQtyLabelForSymbolIsDisplayed(symbol: String) {
        composeTestRule.onNodeWithTag("net_qty_$symbol").assertIsDisplayed()
    }

    fun netQtyValueForSymbolIsDisplayed(symbol: String) {
        composeTestRule.onNodeWithTag("net_qty_value_$symbol").assertIsDisplayed()
    }

    fun pnlLabelForSymbolIsDisplayed(symbol: String) {
        composeTestRule.onNodeWithTag("pnl_$symbol").assertIsDisplayed()
    }

    fun pnlValueForSymbolIsDisplayed(symbol: String) {
        composeTestRule.onNodeWithTag("pnl_value_$symbol").assertIsDisplayed()
    }

    fun symbolIsDisplayed(symbol: String) {
        composeTestRule.onNodeWithText(symbol).assertIsDisplayed()
    }

    fun totalPnlIsDisplayed() {
        composeTestRule.onNodeWithTag("total_pnl_label", useUnmergedTree = true).assertIsDisplayed()
    }

    fun totalPnlValueIsDisplayed() {
        composeTestRule.onNodeWithTag("total_pnl_value", useUnmergedTree = true).assertIsDisplayed()
    }

    fun summaryArrowIsDisplayed() {
        summaryArrow.assertIsDisplayed()
    }

    fun summaryLabelForLabelIsDisplayed(label: String) {
        composeTestRule.onNodeWithTag("summary_label_$label").assertIsDisplayed()
    }

    fun summaryValueForLabelIsDisplayed(label: String) {
        composeTestRule.onNodeWithTag("summary_value_$label").assertIsDisplayed()
    }

    fun summaryLabelForLabelIsNotDisplayed(label: String) {
        composeTestRule.onNodeWithTag("summary_label_$label").assertIsNotDisplayed()
    }

    fun summaryValueForLabelIsNotDisplayed(label: String) {
        composeTestRule.onNodeWithTag("summary_value_$label").assertIsNotDisplayed()
    }

    fun clickRetry() {
        retryCta.performClick()
    }

    fun clickSummeryToggle(){
        summaryArrow.performClick()
    }
}
fun holdingContentRobot(composeTestRule: ComposeTestRule, block: HoldingContentRobot.() -> Unit) {
    HoldingContentRobot(composeTestRule).apply(block)
}