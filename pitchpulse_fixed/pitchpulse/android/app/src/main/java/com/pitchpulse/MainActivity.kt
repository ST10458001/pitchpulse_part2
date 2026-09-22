package com.pitchpulse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.pitchpulse.auth.AuthRepository
import com.pitchpulse.nav.PitchPulseNavGraph
import com.pitchpulse.ui.theme.PitchPulseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val authRepository = AuthRepository(applicationContext)

        setContent {
            PitchPulseTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    PitchPulseNavGraph(authRepository = authRepository)
                }
            }
        }
    }
}
