package dev.drtheo.mes.data

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import dev.drtheo.mes.network.DnevnikApiService
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

interface AuthRepository {
    suspend fun refreshToken(): String
    var refreshingToken: Boolean

    fun saveToken(token: String)
    fun getToken(): String?
    fun getTokenAge(): Long?

    fun hasToken(): Boolean
    fun isTokenExpired(): Boolean
    fun clearToken()
}

class NetworkAuthRepository(
    context: Context,
    private val coreApiService: DnevnikApiService,
    private val tokenExpiryTime: Long = 1000 * 60 * 60 * 24, // 24 hours
) : AuthRepository {
    private val sharedPreferences = EncryptedSharedPreferences.create(
        "auth_prefs", MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC), context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    private var token: String? = null
    private var age: Long? = null

    override var refreshingToken: Boolean = false

    override suspend fun refreshToken(): String {
        refreshingToken = true
        val result = coreApiService.refreshToken()
        saveToken(result)

        refreshingToken = false
        return result
    }

    override fun saveToken(token: String) {
        this.token = token
        this.age = System.currentTimeMillis()

        sharedPreferences.edit().putString("token", token)
            .putLong("age", age!!).apply()
    }

    override fun getToken(): String? {
        if (token == null)
            token = sharedPreferences.getString("token", null)

        return token
    }

    override fun getTokenAge(): Long? {
        if (age == null)
            age = sharedPreferences.getLong("age", -1)
                .let { if (it == -1L) { null } else { it } }

        return age
    }

    override fun hasToken(): Boolean = getToken() != null && getTokenAge() != null

    override fun isTokenExpired(): Boolean {
        if (!hasToken())
            return true

        return System.currentTimeMillis() - getTokenAge()!! > tokenExpiryTime
    }

    override fun clearToken() {
        token = null
        age = null

        sharedPreferences.edit().remove("token")
            .remove("age").apply()
    }
}

class AuthInterceptor(private val appContainer: AppContainer) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder().apply {
            val auth = appContainer.authRepository

            println("Token: ${auth.getToken()}")

            if (auth.isTokenExpired() && !auth.refreshingToken) {
                runBlocking { auth.refreshToken() }
            }

            auth.getToken()?.let { token ->
                addHeader("Authorization", "Bearer $token")
                addHeader("Auth-Token", token)
            }
        }.build()

        return chain.proceed(request)
    }
}