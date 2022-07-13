package shyam.retrofit.testing.response

import retrofit2.converter.gson.GsonConverterFactory

import retrofit2.Retrofit


object RetrofitClient {
    private var retrofit: Retrofit? = null
    private const val BASE_URL = "https://reqres.in/api/"


    val retrofitInstance: Retrofit?
        get() {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }
}