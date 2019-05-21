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
       private const val STATUS_ID = "STATUS_ID"
       fun newInstance(id: Int) = CounterFragment().apply {
           arguments = Bundle().apply {
               putInt(STATUS_ID, id)
           }
       }
    }
}