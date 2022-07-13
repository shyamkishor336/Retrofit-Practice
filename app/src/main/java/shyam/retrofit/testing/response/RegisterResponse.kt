package shyam.retrofit.testing.response

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class RegsiterResponse {
    @SerializedName("response")
    @Expose
    lateinit var response: String
}