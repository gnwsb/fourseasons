package com.example.seasonsapp

import android.content.Context
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit

class SpotifyInterceptor(private val context: Context) : Interceptor {

    private val sharedPreferences = context.getSharedPreferences("spotify_prefs", Context.MODE_PRIVATE)

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking { getToken() }
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()
        return chain.proceed(request)
    }

    private suspend fun getToken(): String {
        val token = sharedPreferences.getString("access_token", null)
        val tokenExpiration = sharedPreferences.getLong("token_expiration", 0L)
        val currentTime = System.currentTimeMillis()

        return if (token == null || currentTime >= tokenExpiration) {
            val newToken = refreshToken()
            sharedPreferences.edit()
                .putString("access_token", newToken.access_token)
                .putLong("token_expiration", currentTime + TimeUnit.SECONDS.toMillis(newToken.expires_in.toLong()))
                .apply()
            newToken.access_token
        } else {
            token
        }
    }

    private suspend fun refreshToken(): AuthResponse {
        val clientId = context.getString(R.string.spotify_client_id)
        val clientSecret = context.getString(R.string.spotify_client_secret)

        val authService = SpotifyAuthService.create()
        return authService.getToken("client_credentials", clientId, clientSecret)
    }
}
