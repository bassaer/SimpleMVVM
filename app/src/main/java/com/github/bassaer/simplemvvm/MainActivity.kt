package com.github.bassaer.simplemvvm

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), CountNavigator {

    companion object {
        const val TAG = "MAIN_VIEW_MODEL_TAG"
    }

    private lateinit var viewModel: CountViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val fragment = findOrCreateViewFragment()
        viewModel = findOrCreateViewModel()
        viewModel.countNavigator = this

        fragment.setViewModel(viewModel)
    }

    override fun countUp() {

    }

    private fun findOrCreateViewFragment(): CounterFragment {
        var fragment = supportFragmentManager?.findFragmentById(R.id.contentFrame) as? CounterFragment
        if (fragment == null) {
            fragment = CounterFragment.newInstance(0)
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
        val viewModel = CountViewModel()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(ViewModelHolder.createContainer(viewModel), TAG)
        transaction.commit()
        return viewModel
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
