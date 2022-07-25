package com.example.task.data.local.api

import com.example.task.data.models.Product
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.concurrent.TimeUnit


interface FakeApiService {
    @GET("categories")
    suspend fun getCategories(): List<String>

    @GET("category/{product}")
    suspend fun getProducts(@Path(value = "product", encoded = true) productType: String): MutableList<Product>

    companion object FakeApi {
        private const val BASE_URL = "https://fakestoreapi.com/products/"

        private val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()

        private val retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .build()

        fun getNetworkInstance(): FakeApiService{
            return retrofit.create(FakeApiService::class.java)
        }
    }
}
