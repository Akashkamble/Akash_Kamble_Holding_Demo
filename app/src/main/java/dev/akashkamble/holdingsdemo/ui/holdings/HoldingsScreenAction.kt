package dev.akashkamble.holdingsdemo.ui.holdings

sealed interface HoldingsScreenAction {
    object RetryEvent: HoldingsScreenAction
    object ToggleSummaryEvent: HoldingsScreenAction
}