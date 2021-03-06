package com.github.bassaer.simplemvvm.userlist

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.github.bassaer.simplemvvm.R
import com.github.bassaer.simplemvvm.ViewModelHolder
import com.github.bassaer.simplemvvm.counter.CounterActivity
import com.github.bassaer.simplemvvm.counter.CounterFragment
import com.github.bassaer.simplemvvm.data.source.local.UserDatabase
import com.github.bassaer.simplemvvm.data.source.local.UserLocalDataSource
import com.github.bassaer.simplemvvm.data.source.local.UserRepository
import com.github.bassaer.simplemvvm.github.GitHubActivity


class UserlistActivity : AppCompatActivity(), UserlistNavigator, UserItemNavigator {

    private lateinit var viewModel: UserlistViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.userlist_act)
        setupToolbar()
        val userlistFragment = findOrCreateViewFragment()
        viewModel = findOrCreateViewModel()
        viewModel.navigator = this
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
        val repository = UserRepository.getInstance(UserLocalDataSource.getInstance(userDao))

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

    override fun openGitHubRepoList() {
        val intent = Intent(this, GitHubActivity::class.java)
        startActivity(intent)
    }

    override fun openCounter(userId: String) {
        val intent = Intent(this, CounterActivity::class.java)
        intent.putExtra(CounterFragment.ARGUMENT_USER_ID, userId)
        startActivityForResult(intent, COUNTER_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode != COUNTER_REQUEST || resultCode != RESULT_OK) {
            return
        }
        viewModel.loadUserlist()
    }

    companion object {
        const val COUNTER_REQUEST = 0
        const val TAG = "USERLIST_VIEWMODEL_TAG"
    }

}