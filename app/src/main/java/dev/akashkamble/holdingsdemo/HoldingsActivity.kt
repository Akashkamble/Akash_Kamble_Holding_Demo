package dev.akashkamble.holdingsdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import dev.akashkamble.holdingsdemo.ui.holdings.composables.HoldingScreen
import dev.akashkamble.holdingsdemo.ui.holdings.viewmodel.HoldingsViewModel
import dev.akashkamble.holdingsdemo.ui.theme.HoldingsTheme

@AndroidEntryPoint
class HoldingsActivity : ComponentActivity() {
    private val viewModel: HoldingsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HoldingsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HoldingScreen(
                        viewModel = viewModel,
                        modifier = Modifier.padding(top = innerPadding.calculateTopPadding())
                    )
                }
            }
        }
    }
}