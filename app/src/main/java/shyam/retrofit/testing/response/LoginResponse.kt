package shyam.retrofit.testing.response

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class Loginresponse {
    @SerializedName("user_id")
    @Expose
    var user_id: String? = null


    @SerializedName("email")
    @Expose
    var email: String? = null
}