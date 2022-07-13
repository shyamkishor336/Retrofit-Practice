package shyam.retrofit.testing

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityViewModel : ViewModel(){

    var recyclerListdata: MutableLiveData<UserList>

    init {
        recyclerListdata = MutableLiveData()
    }

    fun getUserListObservable(): MutableLiveData<UserList> {
        return recyclerListdata
    }

    fun getUserList() {
        val retroInstance = RetroInstance.getRetroInstance().create(RetroService::class.java)
        val call = retroInstance.getUsersList()
        call.enqueue(object : Callback<UserList> {
            override fun onResponse(call: Call<UserList>, response: Response<UserList>) {
                if (response.isSuccessful) {
                    recyclerListdata.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<UserList>, t: Throwable) {
                recyclerListdata.postValue(null)
            }

        })


    }
    fun searchUser(searchText: String) {
        val retroInstance = RetroInstance.getRetroInstance().create(RetroService::class.java)
        val call = retroInstance.searchUsers(searchText)
        call.enqueue(object : Callback<UserList> {
            override fun onResponse(call: Call<UserList>, response: Response<UserList>) {
                if (response.isSuccessful) {
                    recyclerListdata.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<UserList>, t: Throwable) {
                recyclerListdata.postValue(null)
            }

        })


    }
}