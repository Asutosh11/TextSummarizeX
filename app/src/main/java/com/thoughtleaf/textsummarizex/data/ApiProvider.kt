package com.thoughtleaf.textsummarizex.data

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiProvider {

    companion object {
        private val BASE_URL = "http://domain"

        fun provideApi(): SummaryService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(provideOkHttpClient(provideLoggingInterceptor()))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
                .create(SummaryService::class.java)
        }

        private fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
            val b = OkHttpClient.Builder()
            b.addInterceptor(interceptor)
            return b.build()
        }

        private fun provideLoggingInterceptor(): HttpLoggingInterceptor {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            return interceptor
        }
    }
}