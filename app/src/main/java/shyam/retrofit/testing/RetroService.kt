package shyam.retrofit.testing

import retrofit2.Call
import retrofit2.http.*

interface RetroService {

    //curl -i -H "Accept:application/json" -H "Content-Type:application/json" -H "Authorization: Bearer a614b61ae862f6513aea845d599e6c5755b61e7fc1231fff9eb299fd4abb1bdf" -XGET "https://gorest.co.in/public/v2/users"

    //https://gorest.co.in/public/v2/users
    @GET("users")
    @Headers("Accept:application/json", "Content-Type:application/json")
    fun getUsersList(): Call<UserList>

    //https://gorest.co.in/public/v2/users?name=a
    @GET("users")
    @Headers("Accept:application/json", "Content-Type:application/json")
    fun searchUsers(@Query("name") searchText: String): Call<UserList>

    //https://gorest.co.in/public/v2/users/121
    @GET("users/{user_id}")
    @Headers("Accept:application/json", "Content-Type:application/json")
    fun getUser(@Path("user_id") user_id: String): Call<UserResponse>



    @POST("users")
    @Headers("Accept:application/json", "Content-Type:application/json",
        "Authorization: Bearer a614b61ae862f6513aea845d599e6c5755b61e7fc1231fff9eb299fd4abb1bdf")
    fun createUser(@Body params: User): Call<UserResponse>

    @PATCH("users/{user_id}")
    @Headers("Accept:application/json","Content-Type:application/json",
        "Authorization: Bearer a614b61ae862f6513aea845d599e6c5755b61e7fc1231fff9eb299fd4abb1bdf")
    fun updateUser(@Path("user_id") user_id: String, @Body params: User): Call<UserResponse>

    @DELETE("users/{user_id}")
    @Headers("Accept:application/json","Content-Type:application/json",
        "Authorization: Bearer a614b61ae862f6513aea845d599e6c5755b61e7fc1231fff9eb299fd4abb1bdf")
    fun deleteUser(@Path("user_id") user_id: String): Call<UserResponse>

}