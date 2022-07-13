package shyam.retrofit.testing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import shyam.retrofit.testing.databinding.ActivityLoginBinding
import shyam.retrofit.testing.databinding.ActivityRegisterBinding
import android.widget.Toast

import org.json.JSONException

import android.R.attr.password

import retrofit2.converter.gson.GsonConverterFactory

import retrofit2.Retrofit

import android.view.WindowManager

import android.R
import android.R.attr

import android.graphics.drawable.Drawable

import android.os.Build

import android.app.Activity
import android.util.Log
import android.view.MenuItem
import android.view.Window
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import shyam.retrofit.testing.response.API
import shyam.retrofit.testing.response.API.Companion.BaseURL
import shyam.retrofit.testing.response.RegsiterResponse


class RegisterActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegisterBinding
    lateinit var email: String
    lateinit var password: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.registerBtn.setOnClickListener {
            email = binding.emailLogin.getText().toString()
            password = binding.passwordLogin.getText().toString();

            Log.d("userdata", "onClick: " + email + password);
            registerMe();
        }

        binding.gotoLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun registerMe() {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BaseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api: API = retrofit.create(API::class.java)
        val call: Call<RegsiterResponse?>? = api.getUserRegi(email, password)
        if (call != null) {
            call.enqueue(object : Callback<RegsiterResponse?> {
                override fun onResponse(
                    call: Call<RegsiterResponse?>?,
                    response: Response<RegsiterResponse?>
                ) {
                    Log.i("Responsestring", response.body().toString())
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            //Log.d("responseLog", response.body().getResponse())
                            Log.i("onSuccess", response.body().toString())
                            val jsonresponse: String = response.body()!!.response
                            try {
                                parseRegData(jsonresponse)
                            } catch (e: JSONException) {
                                e.printStackTrace()
                            }
                        } else {
                            Log.i(
                                "onEmptyResponse",
                                "Returned empty response"
                            ) //Toast.makeText(getContext(),"Nothing returned",Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(this@RegisterActivity, "Something went wrong!!",Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<RegsiterResponse?>?, t: Throwable) {
                    Log.d("go", "onFailure: $t")
                }
            })
        }
    }



    private fun parseRegData(response: String) {
        Log.d("juststring", response)
        if (response == "success") {
            Toast.makeText(this@RegisterActivity, "Registered Successfully!", Toast.LENGTH_SHORT)
                .show()
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            finish()
            startActivity(intent)
        } else {
            Toast.makeText(this@RegisterActivity, "OOPS", Toast.LENGTH_SHORT).show()
        }
    }
}