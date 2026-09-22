package com.pitchpulse.auth

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.pitchpulse.network.AuthResponse
import com.pitchpulse.network.LoginRequest
import com.pitchpulse.network.RegisterRequest
import com.pitchpulse.network.RetrofitClient

private const val TAG = "AuthRepository"
private const val PREFS_NAME = "pitchpulse_prefs"
private const val KEY_TOKEN = "auth_token"
private const val KEY_UID = "auth_uid"

sealed class AuthResult {
    data class Success(val response: AuthResponse) : AuthResult()
    data class Error(val message: String) : AuthResult()
}

class AuthRepository(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    suspend fun register(email: String, password: String, displayName: String?): AuthResult {
        return try {
            val response = RetrofitClient.api.register(RegisterRequest(email, password, displayName))
            if (response.isSuccessful && response.body() != null) {
                val body = response.body()!!
                saveSession(body.uid, body.token)
                Log.i(TAG, "Registered user ${body.uid}")
                AuthResult.Success(body)
            } else {
                AuthResult.Error(response.errorBody()?.string() ?: "Registration failed.")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Register failed", e)
            AuthResult.Error("Network error — check your connection and try again.")
        }
    }

    suspend fun login(email: String, password: String): AuthResult {
        return try {
            val response = RetrofitClient.api.login(LoginRequest(email, password))
            if (response.isSuccessful && response.body() != null) {
                val body = response.body()!!
                saveSession(body.uid, body.token)
                Log.i(TAG, "Logged in user ${body.uid}")
                AuthResult.Success(body)
            } else {
                AuthResult.Error("Invalid email or password.")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Login failed", e)
            AuthResult.Error("Network error — check your connection and try again.")
        }
    }

    fun logout() {
        prefs.edit().clear().apply()
    }

    fun getToken(): String? = prefs.getString(KEY_TOKEN, null)

    fun isLoggedIn(): Boolean = getToken() != null

    private fun saveSession(uid: String, token: String) {
        prefs.edit()
            .putString(KEY_UID, uid)
            .putString(KEY_TOKEN, token)
            .apply()
    }
}
