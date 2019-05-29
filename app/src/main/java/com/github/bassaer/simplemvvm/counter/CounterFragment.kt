package com.github.bassaer.simplemvvm.counter

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.github.bassaer.simplemvvm.R
import com.github.bassaer.simplemvvm.databinding.CounterFragBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CounterFragment: Fragment(){
    lateinit var countViewModel: CountViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupFab()
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.counter_frag, container, false)
        val counterFragBinding = CounterFragBinding.bind(view)
        counterFragBinding.viewmodel = countViewModel
        return view
    }

    override fun onResume() {
        super.onResume()
        val userId = arguments?.getString(ARGUMENT_USER_ID) ?: return
        countViewModel.loadUser(userId)
    }

    override fun onPause() {
        super.onPause()
        countViewModel.saveCount()
    }

    private fun setupFab() {
        val fab = activity?.findViewById<FloatingActionButton>(R.id.fab)
        fab?.setOnClickListener {
            countViewModel.countUp()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_reset -> {
                countViewModel.reset()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        const val ARGUMENT_USER_ID = "USER_ID"
        fun newInstance(userId: String) =
            CounterFragment().apply {
                arguments = Bundle().apply {
                    putString(ARGUMENT_USER_ID, userId)
                }
            }
    }
}