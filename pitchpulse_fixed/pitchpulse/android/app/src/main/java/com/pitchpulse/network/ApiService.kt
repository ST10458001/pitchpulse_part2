package com.pitchpulse.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

data class RegisterRequest(val email: String, val password: String, val displayName: String? = null)
data class LoginRequest(val email: String, val password: String)
data class AuthResponse(
    val uid: String,
    val email: String,
    val displayName: String? = null,
    val token: String
)

data class SettingsResponse(
    val theme: String,
    val notifyGoalsOnly: Boolean,
    val notifyAllEvents: Boolean
)

data class Match(
    val id: String,
    val home: String,
    val away: String,
    val score: String,
    val minute: Int?,
    val status: String
)
data class MatchesResponse(val source: String, val matches: List<Match>)

data class ApiError(val error: String)

interface ApiService {

    @POST("v1/auth/register")
    suspend fun register(@Body body: RegisterRequest): Response<AuthResponse>

    @POST("v1/auth/login")
    suspend fun login(@Body body: LoginRequest): Response<AuthResponse>

    @GET("v1/settings")
    suspend fun getSettings(@Header("Authorization") bearerToken: String): Response<SettingsResponse>

    @PUT("v1/settings")
    suspend fun updateSettings(
        @Header("Authorization") bearerToken: String,
        @Body body: Map<String, Any>
    ): Response<SettingsResponse>

    @GET("v1/matches")
    suspend fun getMatches(): Response<MatchesResponse>
}
