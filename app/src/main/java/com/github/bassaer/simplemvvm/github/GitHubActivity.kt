package com.github.bassaer.simplemvvm.github

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.github.bassaer.simplemvvm.R
import com.github.bassaer.simplemvvm.ViewModelHolder
import com.github.bassaer.simplemvvm.data.remote.RemoteRepository

class GitHubActivity : AppCompatActivity() {

    lateinit var viewModel: GitHubViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.github_act)
        setupToolbar()
        val fragment = findOrCreateViewFragment()
        fragment.viewModel = findOrCreateViewModel().also {
           viewModel = it
        }
    }

    private fun setupToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setHomeAsUpIndicator(R.drawable.ic_menu)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun findOrCreateViewFragment(): GitHubFragment {
        var fragment = supportFragmentManager.findFragmentById(R.id.contentFrame) as? GitHubFragment
        if (fragment == null) {
            fragment = GitHubFragment.newInstance()
            val transaction = supportFragmentManager.beginTransaction()
            transaction.add(R.id.contentFrame, fragment)
            transaction.commit()
        }
        return fragment
    }

    private fun findOrCreateViewModel(): GitHubViewModel {
        @Suppress("UNCHECKED_CAST")
        val retainedViewModel = supportFragmentManager.findFragmentByTag(TAG) as? ViewModelHolder<GitHubViewModel>
        retainedViewModel?.viewModel?.let {
            return it
        }
        val viewModel = GitHubViewModel(RemoteRepository.getInstance())
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(ViewModelHolder.createContainer(viewModel), TAG)
        transaction.commit()
        return viewModel
    }

    companion object {
        const val TAG = "GitHubActivity"
    }
}