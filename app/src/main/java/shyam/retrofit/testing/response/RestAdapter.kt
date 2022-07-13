package shyam.retrofit.testing.response

import retrofit2.converter.gson.GsonConverterFactory

import retrofit2.Retrofit

import okhttp3.OkHttpClient

import okhttp3.logging.HttpLoggingInterceptor
import shyam.retrofit.testing.BuildConfig
import shyam.retrofit.testing.response.API.Companion.BaseURL
import java.util.concurrent.TimeUnit


object RestAdapter {
    fun createAPI(): API {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(5, TimeUnit.SECONDS)
        builder.writeTimeout(10, TimeUnit.SECONDS)
        builder.readTimeout(30, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(logging)
        }
        builder.cache(null)
        val okHttpClient: OkHttpClient = builder.build()
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BaseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        return retrofit.create(API::class.java)
    }
}