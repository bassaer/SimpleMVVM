package com.github.bassaer.simplemvvm.counter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.bassaer.simplemvvm.R
import com.github.bassaer.simplemvvm.ViewModelHolder
import com.github.bassaer.simplemvvm.data.source.local.UserDatabase
import com.github.bassaer.simplemvvm.data.source.local.UserLocalDataSource
import com.github.bassaer.simplemvvm.data.source.local.UserRepository
import kotlinx.android.synthetic.main.counter_act.*

class CounterActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MAIN_VIEW_MODEL_TAG"
    }

    private lateinit var viewModel: CountViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.counter_act)
        setSupportActionBar(toolbar)
        supportActionBar?.run {
            setDisplayShowHomeEnabled(true)
        }

        val fragment = findOrCreateViewFragment()
        viewModel = findOrCreateViewModel()

        fragment.countViewModel = viewModel
    }

    private fun findOrCreateViewFragment(): CounterFragment {
        var fragment = supportFragmentManager.findFragmentById(R.id.contentFrame) as? CounterFragment
        if (fragment == null) {
            val userId = intent.getStringExtra(CounterFragment.ARGUMENT_USER_ID)
            fragment = CounterFragment.newInstance(userId)
            val transaction = supportFragmentManager.beginTransaction()
            transaction.add(R.id.contentFrame, fragment)
            transaction.commit()
        }
        return fragment
    }

    private fun findOrCreateViewModel(): CountViewModel {
        @Suppress("UNCHECKED_CAST")
        val retainViewModel = supportFragmentManager.findFragmentByTag(TAG) as? ViewModelHolder<CountViewModel>
        if (retainViewModel?.viewModel != null) {
            return retainViewModel.viewModel as CountViewModel
        }

        // TODO Inject
        val userDao = UserDatabase.getInstance(applicationContext).userDao()
        val repository = UserRepository.getInstance(UserLocalDataSource.getInstance(userDao))

        val viewModel = CountViewModel(repository)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(
            ViewModelHolder.createContainer(viewModel),
            TAG
        )
        transaction.commit()
        return viewModel
    }

}
