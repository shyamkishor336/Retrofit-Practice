package shyam.retrofit.testing

import android.service.autofill.UserData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateUserViewModel : ViewModel() {
    lateinit var createUserLiveData: MutableLiveData<UserResponse?>
    lateinit var loadUserData: MutableLiveData<UserResponse?>
    lateinit var deleteUserData: MutableLiveData<UserResponse?>


    init {
        createUserLiveData = MutableLiveData()
        loadUserData = MutableLiveData()
        deleteUserData = MutableLiveData()
    }


    fun getCreateUserObervable(): MutableLiveData<UserResponse?> {
        return createUserLiveData
    }

    fun getLoadUserObeservable(): MutableLiveData<UserResponse?> {
        return loadUserData
    }

    fun getDeleteUserObeservable(): MutableLiveData<UserResponse?> {
        return deleteUserData
    }


    fun createUser(user: User) {
        val retroInstance = RetroInstance.getRetroInstance().create(RetroService::class.java)
        val call = retroInstance.createUser(user)
        call.enqueue(object : Callback<UserResponse?> {
            override fun onResponse(call: Call<UserResponse?>, response: Response<UserResponse?>) {
                if (response.isSuccessful) {
                    createUserLiveData.postValue(response.body())
                } else {
                    createUserLiveData.postValue(null)
                }
            }

            override fun onFailure(call: Call<UserResponse?>, t: Throwable) {
                createUserLiveData.postValue(null)
            }

        })
    }

    fun updateUser(userid: String, user: User) {
        val retroInstance = RetroInstance.getRetroInstance().create(RetroService::class.java)
        val call = retroInstance.updateUser(userid, user)
        call.enqueue(object : Callback<UserResponse?> {
            override fun onResponse(call: Call<UserResponse?>, response: Response<UserResponse?>) {
                if (response.isSuccessful) {
                    createUserLiveData.postValue(response.body())
                } else {
                    createUserLiveData.postValue(null)
                }
            }

            override fun onFailure(call: Call<UserResponse?>, t: Throwable) {
                createUserLiveData.postValue(null)
            }

        })
    }
fun deleteUser( userid: String) {
        val retroInstance = RetroInstance.getRetroInstance().create(RetroService::class.java)
        val call = retroInstance.deleteUser(userid)
        call.enqueue(object : Callback<UserResponse?> {
            override fun onResponse(call: Call<UserResponse?>, response: Response<UserResponse?>) {
                if (response.isSuccessful) {
                    deleteUserData.postValue(response.body())
                } else {
                    deleteUserData.postValue(null)
                }
            }

            override fun onFailure(call: Call<UserResponse?>, t: Throwable) {
                deleteUserData.postValue(null)
            }

        })
    }

    fun getUserData(user_id: String?) {
        val retroInstance = RetroInstance.getRetroInstance().create(RetroService::class.java)
        val call = retroInstance.getUser(user_id!!)
        call.enqueue(object : Callback<UserResponse?> {
            override fun onResponse(call: Call<UserResponse?>, response: Response<UserResponse?>) {
                if (response.isSuccessful) {
                    loadUserData.postValue(response.body())
                } else {
                    loadUserData.postValue(null)
                }
            }

            override fun onFailure(call: Call<UserResponse?>, t: Throwable) {
                loadUserData.postValue(null)
            }

        })
    }

}