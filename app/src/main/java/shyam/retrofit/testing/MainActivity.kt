package shyam.retrofit.testing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import shyam.retrofit.testing.databinding.ActivityMainBinding

lateinit var binding: ActivityMainBinding
lateinit var recyclerviewAdapter: RecyclerviewAdapter
lateinit var viewModel: MainActivityViewModel

class MainActivity : AppCompatActivity(),RecyclerviewAdapter.OnItemEditClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerview()
        initViewModel()
        searchUser()

        binding.login.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        binding.createUser.setOnClickListener {
            startActivity(Intent(this, CreateNewUserActivity::class.java))
        }
    }

    private fun searchUser() {
        binding.searchBtn.setOnClickListener {
            if (!TextUtils.isEmpty(binding.searchtext.text.toString())){
                viewModel.searchUser(binding.searchtext.text.toString())
            }else{
                viewModel.getUserList()
            }
        }
    }

    private fun initRecyclerview() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
//            val decoration =
//                DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL)
//            addItemDecoration(decoration)
            recyclerviewAdapter = RecyclerviewAdapter(this@MainActivity)
            adapter = recyclerviewAdapter


        }
    }

   private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        viewModel.getUserListObservable().observe(this, Observer<UserList> {
            if (it==null){
                Toast.makeText(this,"no result found...",Toast.LENGTH_LONG).show()
            }else{
                recyclerviewAdapter.userList = it.data.toMutableList()
                recyclerviewAdapter.notifyDataSetChanged()
            }
        })
       viewModel.getUserList()
    }

    override fun onItemClick(user: User) {
        val intent = Intent(this,CreateNewUserActivity::class.java)
        intent.putExtra("user_id",user.id)
        startActivityForResult(intent,1000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode==1000){
            viewModel.getUserList()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}