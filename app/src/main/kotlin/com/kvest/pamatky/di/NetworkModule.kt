package com.kvest.pamatky.di

import com.kvest.pamatky.api.SightsApi
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import timber.log.Timber
import java.util.concurrent.TimeUnit

val apiModule = module {
    single<SightsApi> { buildSightsApi() }
}

private val CONNECT_TIMEOUT_SECONDS = 30L
private val READ_TIMEOUT_SECONDS = 60L
private val BASE_URL = "https://klickpamatkam.cz/"
val POTO_URL = "https://klickpamatkam.cz/Api/v1/Soubor/%s"

private fun buildOkHttpClient(): OkHttpClient {
    val okHttpBuilder = OkHttpClient.Builder()
        .connectTimeout(CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .readTimeout(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
    //interceptor
    okHttpBuilder.addNetworkInterceptor(HttpLoggingInterceptor(
        object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Timber.tag("okhttp").d(message)
            }
        }
    ).apply {
        level = HttpLoggingInterceptor.Level.BODY
    })

    return okHttpBuilder.build()
}

private fun buildMoshi() = Moshi.Builder().build()

private fun buildSightsApi(): SightsApi {
    val client = buildOkHttpClient()
    val moshi = buildMoshi()

    val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .client(client)
        .build()

    return retrofit.create<SightsApi>()
}