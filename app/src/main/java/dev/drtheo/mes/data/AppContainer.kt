package dev.drtheo.mes.data

import android.content.Context
import dev.drtheo.mes.network.DnevnikApiService
import kotlinx.serialization.json.Json
import okhttp3.Cache
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.io.File

/**
 * Dependency Injection container at the application level.
 */
interface AppContainer {
    val authRepository: AuthRepository
    val dnevnikRepository: DnevnikRepository
}

/**
 * Implementation for the Dependency Injection container at the application level.
 *
 * Variables are initialized lazily and the same instance is shared across the whole app.
 */
class DefaultAppContainer(context: Context) : AppContainer {
    private val baseUrl = "https://school.mos.ru/"

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor(this))
        .cache(Cache(
            directory = File(context.cacheDir, "http_cache"),
            maxSize = 10 * 1024 * 1024 // 10 MB
        )).build();

    /**
     * Use the Retrofit builder to build a retrofit object using a kotlinx.serialization converter
     */
    private val retrofitCore: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json {
            ignoreUnknownKeys = true
        }.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .build()

    /**
     * Retrofit service object for creating api calls
     */
    private val retrofitService: DnevnikApiService by lazy {
        retrofitCore.create(DnevnikApiService::class.java)
    }

    override val authRepository: AuthRepository by lazy {
        NetworkAuthRepository(context, retrofitService)
    }

    override val dnevnikRepository: DnevnikRepository by lazy {
        NetworkDnevnikRepository(retrofitService)
    }
}