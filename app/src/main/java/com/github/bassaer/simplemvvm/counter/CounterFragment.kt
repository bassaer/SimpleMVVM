package com.github.bassaer.simplemvvm.counter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.bassaer.simplemvvm.R
import com.github.bassaer.simplemvvm.databinding.CounterFragBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CounterFragment: Fragment(){
    private var countViewModel: CountViewModel? = null

    fun setViewModel(viewModel: CountViewModel) {
        countViewModel = viewModel
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupFab()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.counter_frag, container, false)
        val counterFragBinding = CounterFragBinding.bind(view)
        counterFragBinding.viewmodel = countViewModel
        return view
    }

    private fun setupFab() {
        val fab = activity?.findViewById<FloatingActionButton>(R.id.fab)
        fab?.setOnClickListener {
            countViewModel?.countUp()
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