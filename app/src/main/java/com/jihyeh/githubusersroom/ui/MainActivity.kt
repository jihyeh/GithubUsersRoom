package com.jihyeh.githubusersroom.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.jihyeh.githubusersroom.BaseApplication
import com.jihyeh.githubusersroom.R
import com.jihyeh.githubusersroom.data.GithubRepository
import com.jihyeh.githubusersroom.data.GithubRepositoryImpl
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository: GithubRepository = GithubRepositoryImpl((application as BaseApplication).database.userDao())
        val viewModel = ViewModelProvider(
                this,
                SearchUserViewModelFactory(repository)
            )[SearchUserViewModel::class.java]

        viewModel.users.observe(this) {
            Log.w("hjh", "MainActivity, it: $it")
        }

        viewModel.showToast.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        findViewById<Button>(R.id.userBtn).setOnClickListener {
            viewModel.getUsers()
        }

        findViewById<Button>(R.id.clearBtn).setOnClickListener {
            viewModel.clearUsers()
        }

    }
}