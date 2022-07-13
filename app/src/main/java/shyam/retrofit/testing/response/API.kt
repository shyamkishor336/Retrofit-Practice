package shyam.retrofit.testing.response

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.POST

import retrofit2.http.FormUrlEncoded


interface API {
    //register
    @FormUrlEncoded
    @POST("register/")
    fun getUserRegi(
        @Field("email") email: String?,
        @Field("password") password: String?
    ): Call<RegsiterResponse?>?


    //login
    @FormUrlEncoded
    @POST("login/")
    fun getUserLogin(
        @Field("email") email: String?,
        @Field("password") password: String?
    ): Call<LoginUserResponse?>?

    companion object {

        const val BaseURL = "https://reqres.in/api/"
    }
}