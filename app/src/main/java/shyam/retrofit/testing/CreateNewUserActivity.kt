package shyam.retrofit.testing

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import shyam.retrofit.testing.databinding.ActivityCreateNewUserBinding
import shyam.retrofit.testing.databinding.ActivityMainBinding

class CreateNewUserActivity : AppCompatActivity() {

    lateinit var binding: ActivityCreateNewUserBinding
    lateinit var viewModel: CreateUserViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateNewUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val userid = intent.getStringExtra("user_id")
        initViewModel()
        createUserObservable()
        if (userid != null) {
            loadUserData(userid)
        }
        binding.createUserBtn.setOnClickListener {
            createUser(userid)
        }
        binding.deleteBtn.setOnClickListener {
            deleteUser(userid)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
    private fun deleteUser(userid: String?) {

        viewModel.getDeleteUserObeservable().observe(this, Observer<UserResponse?> {
            if (it == null) {
                Toast.makeText(this, "failed to delete user...", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "successfully deleted user...", Toast.LENGTH_LONG).show()
                finish()
            }
        })
        viewModel.deleteUser(userid!!)
    }

    private fun loadUserData(user_id: String) {
        viewModel.getLoadUserObeservable().observe(this, Observer<UserResponse?> {
            if (it != null) {
               binding.nameText.setText(it.data?.name)
               binding.emailText.setText(it.data?.email)
                binding.createUserBtn.setText("Update")
                binding.deleteBtn.visibility = View.VISIBLE
            }
        })
        viewModel.getUserData(user_id)
    }

    private fun createUser(user_id: String?){
        val user = User("",binding.nameText.text.toString(), binding.emailText.text.toString(),"Active","Male")

        if(user_id == null)
            viewModel.createUser(user)
        else
            viewModel.updateUser(user_id, user)

    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(CreateUserViewModel::class.java)

    }

    private fun createUserObservable() {
        viewModel.getCreateUserObervable().observe(this, Observer<UserResponse?> {
            if (it == null) {
                Toast.makeText(this, "failed to create new user...", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "successfully created new user...", Toast.LENGTH_LONG).show()
                finish()
            }
        })
    }
}