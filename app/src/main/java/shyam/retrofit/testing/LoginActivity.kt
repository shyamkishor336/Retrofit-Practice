package shyam.retrofit.testing

import android.R.attr
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import shyam.retrofit.testing.databinding.ActivityCreateNewUserBinding
import shyam.retrofit.testing.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar

import android.R.attr.password
import android.text.AlteredCharSequence.make
import android.util.Log

import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar.make
import android.R.attr.password
import android.content.Context
import android.content.SharedPreferences
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.RelativeLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import shyam.retrofit.testing.response.API
import shyam.retrofit.testing.response.LoginUserResponse
import shyam.retrofit.testing.response.RestAdapter
import java.lang.Exception
import java.util.regex.Pattern


class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    lateinit var email: String
    lateinit var password: String

    var sharedPreferences: SharedPreferences? = null
    var editor: SharedPreferences.Editor? = null
    val SHARED_PREFERENCES_NAME = "login_portal"
    val USER_ID = "user_id"
    val FNAME = "fname"
    val LNAME = "lname"
    val EMAIL = "email"
    val PASSWORD = "password"
    private val parent_view: View? = null
    var rl_pwd: RelativeLayout? = null
    var ll_lay: LinearLayout? = null
    var pattern_pwd = Pattern.compile("^[a-zA-Z0-9]+$")
    var userid = ""
    var useremail: String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.skipp.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        binding.gotoRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        binding.loginBtn.setOnClickListener {
            email = binding.emailLogin.getText().toString().trim()
            password = binding.passwordLogin.getText().toString().trim()
            Log.d("userdata", "onClick: " + email + password)
            if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                if (!password.isEmpty()) {
                    loginUser()
                } else {
                    Toast.makeText(this, "Enter a valid password", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "Enter a valid email", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun loginUser() {
        val api: API = RestAdapter.createAPI()

//        API service = RetrofitClient.getRetrofitInstance().create(API.class);

//        API service = RetrofitClient.getRetrofitInstance().create(API.class);
        Log.d("call_call", "onClick: " + email + password)
        val call: Call<LoginUserResponse?>? = api.getUserLogin(email, password)
        if (call != null) {
            call.enqueue(object : Callback<LoginUserResponse?> {
                override fun onResponse(
                    call: Call<LoginUserResponse?>?,
                    response: Response<LoginUserResponse?>
                ) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            //Log.i("onSuccess", response.body().loginresponse
                            userid = response.body()!!.loginresponse!!.user_id!!
                            useremail = response.body()!!.loginresponse!!.email

                            try {
                                parseLoginData()
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        } else {
                            Log.i(
                                "onEmptyResponse",
                                "Returned empty response"
                            ) //Toast.makeText(getContext(),"Nothing returned",Toast.LENGTH_LONG).show();
                        }
                    }
                }

                override fun onFailure(call: Call<LoginUserResponse?>?, t: Throwable) {
                    Log.d("LoginBHai_error", "onFailure: Throw$t")
                }
            })
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
    private fun parseLoginData() {
        try {
            if (!useremail!!.isEmpty()) {
                saveInfo()
            } else {
                Log.d("loginresponse", "empty")
            }
            Toast.makeText(this@LoginActivity, "Login Successfully!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun saveInfo() {
        try {
            Log.d("sherf_1", "called$useremail")
            if (!useremail!!.isEmpty()) {
                Log.d("sherf", "called$useremail")
                sharedPreferences =
                    getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
                editor = sharedPreferences!!.edit()
                editor!!.putString(USER_ID, userid)
//                editor.putString(FNAME, userfname)
//                editor.putString(LNAME, userlname)
                editor!!.putString(EMAIL, useremail)
                editor!!.apply()
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                //ye ata toast
                Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}