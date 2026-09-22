package com.pitchpulse.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pitchpulse.network.Match
import com.pitchpulse.network.RetrofitClient
import com.pitchpulse.ui.theme.Charcoal
import com.pitchpulse.ui.theme.CharcoalLight
import com.pitchpulse.ui.theme.CyanPulse
import kotlinx.coroutines.launch

private const val TAG = "HomeFeedScreen"

@Composable
fun HomeFeedScreen() {
    var matches by remember { mutableStateOf<List<Match>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    suspend fun loadMatches() {
        isLoading = true
        errorMessage = null
        try {
            val response = RetrofitClient.api.getMatches()
            if (response.isSuccessful && response.body() != null) {
                matches = response.body()!!.matches
                Log.i(TAG, "Loaded ${matches.size} matches (source=${response.body()!!.source})")
            } else {
                errorMessage = "Could not load matches right now."
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to load matches", e)
            errorMessage = "Network error — check your connection."
        } finally {
            isLoading = false
        }
    }

    LaunchedEffect(Unit) { loadMatches() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Charcoal)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Live & upcoming", style = MaterialTheme.typography.headlineSmall, color = CyanPulse)
            TextButton(onClick = { scope.launch { loadMatches() } }) {
                Text("Refresh", color = Color.White.copy(alpha = 0.8f))
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        when {
            isLoading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = CyanPulse)
            }
            errorMessage != null -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(errorMessage!!, color = Color.White.copy(alpha = 0.7f))
            }
            matches.isEmpty() -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No matches right now.", color = Color.White.copy(alpha = 0.6f))
            }
            else -> LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(matches) { match -> MatchRow(match) }
            }
        }
    }
}

@Composable
private fun MatchRow(match: Match) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(CharcoalLight)
            .padding(14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text("${match.home} vs ${match.away}", color = Color.White, style = MaterialTheme.typography.bodyLarge)
            Text(
                text = if (match.status == "live") "Live · ${match.minute ?: 0}'" else match.status.replaceFirstChar { it.uppercase() },
                color = if (match.status == "live") CyanPulse else Color.White.copy(alpha = 0.6f),
                style = MaterialTheme.typography.bodySmall
            )
        }
        Text(match.score, color = Color.White, style = MaterialTheme.typography.headlineSmall)
    }
}
