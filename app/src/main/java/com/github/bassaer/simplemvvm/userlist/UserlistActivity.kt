package com.github.bassaer.simplemvvm.userlist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.github.bassaer.simplemvvm.R
import com.github.bassaer.simplemvvm.ViewModelHolder
import com.github.bassaer.simplemvvm.data.local.UserDatabase
import com.github.bassaer.simplemvvm.data.local.UserRepository


class UserlistActivity : AppCompatActivity(), UserlistNavigator {

    companion object {
        const val TAG = "USERLIST_VIEWMODEL_TAG"
    }

    private lateinit var viewModel: UserlistViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.userlist_act)
        setupToolbar()
        val userlistFragment = findOrCreateViewFragment()
        viewModel = findOrCreateViewModel()
        userlistFragment.viewModel = viewModel
    }

    private fun setupToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setHomeAsUpIndicator(R.drawable.ic_menu)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun findOrCreateViewFragment(): UserlistFragment {
        var fragment = supportFragmentManager.findFragmentById(R.id.contentFrame) as? UserlistFragment
        if (fragment == null) {
            fragment = UserlistFragment.newInstance()
            val transaction = supportFragmentManager.beginTransaction()
            transaction.add(R.id.contentFrame, fragment)
            transaction.commit()
        }
        return fragment
    }

    private fun findOrCreateViewModel(): UserlistViewModel {
        @Suppress("UNCHECKED_CAST")
        val retainedViewModel = supportFragmentManager.findFragmentByTag(TAG) as? ViewModelHolder<UserlistViewModel>

        retainedViewModel?.viewModel?.let {
            return it
        }

        // TODO Inject
        val userDao = UserDatabase.getInstance(applicationContext).userDao()
        val repository = UserRepository(userDao)

        val viewModel = UserlistViewModel(repository)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(ViewModelHolder.createContainer(viewModel), TAG)
        transaction.commit()
        return viewModel
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onActivityDestroyed()
    }

    override fun addNewUser() {
        // will remove this func
    }
}