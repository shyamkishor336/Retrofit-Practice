package shyam.retrofit.testing.response

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class LoginUserResponse {
    @SerializedName("response")
    @Expose

     var loginresponse: Loginresponse? = null

}