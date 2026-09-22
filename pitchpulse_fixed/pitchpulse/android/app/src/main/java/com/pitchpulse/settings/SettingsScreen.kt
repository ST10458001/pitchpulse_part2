package com.pitchpulse.settings

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pitchpulse.auth.AuthRepository
import com.pitchpulse.auth.PitchPulseCharcoal
import com.pitchpulse.auth.PitchPulseCyan
import com.pitchpulse.network.RetrofitClient
import kotlinx.coroutines.launch

private const val TAG = "SettingsScreen"

@Composable
fun SettingsScreen(authRepository: AuthRepository) {
    val scope = rememberCoroutineScope()
    var notifyGoalsOnly by remember { mutableStateOf(true) }
    var notifyAllEvents by remember { mutableStateOf(false) }
    var isSaving by remember { mutableStateOf(false) }
    var statusMessage by remember { mutableStateOf<String?>(null) }

    val token = authRepository.getToken()

    LaunchedEffect(Unit) {
        if (token != null) {
            try {
                val response = RetrofitClient.api.getSettings("Bearer $token")
                response.body()?.let {
                    notifyGoalsOnly = it.notifyGoalsOnly
                    notifyAllEvents = it.notifyAllEvents
                }
            } catch (e: Exception) {
                Log.e(TAG, "Failed to load settings", e)
            }
        }
    }

    fun saveSettings() {
        if (token == null) return
        isSaving = true
        scope.launch {
            try {
                RetrofitClient.api.updateSettings(
                    "Bearer $token",
                    mapOf(
                        "notifyGoalsOnly" to notifyGoalsOnly,
                        "notifyAllEvents" to notifyAllEvents
                    )
                )
                statusMessage = "Settings saved."
                Log.i(TAG, "Settings updated")
            } catch (e: Exception) {
                statusMessage = "Could not save settings — check your connection."
                Log.e(TAG, "Failed to save settings", e)
            } finally {
                isSaving = false
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PitchPulseCharcoal)
            .padding(24.dp)
    ) {
        Text("Notification settings", style = MaterialTheme.typography.headlineSmall, color = PitchPulseCyan)
        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Goals only", color = androidx.compose.ui.graphics.Color.White)
            Switch(
                checked = notifyGoalsOnly,
                onCheckedChange = {
                    notifyGoalsOnly = it
                    if (it) notifyAllEvents = false
                    saveSettings()
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("All match events", color = androidx.compose.ui.graphics.Color.White)
            Switch(
                checked = notifyAllEvents,
                onCheckedChange = {
                    notifyAllEvents = it
                    if (it) notifyGoalsOnly = false
                    saveSettings()
                }
            )
        }

        statusMessage?.let {
            Spacer(modifier = Modifier.height(16.dp))
            Text(it, color = androidx.compose.ui.graphics.Color.White.copy(alpha = 0.7f), style = MaterialTheme.typography.bodySmall)
        }

        if (isSaving) {
            Spacer(modifier = Modifier.height(8.dp))
            CircularProgressIndicator(modifier = Modifier.size(20.dp), color = PitchPulseCyan)
        }
    }
}
