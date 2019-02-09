package com.github.bassaer.simplemvvm

import android.os.Bundle
import android.support.v4.app.Fragment

class ViewModelHolder<VM> : Fragment() {

    var viewModel: VM? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    companion object {
        fun <M>createContainer(viewModel: M): ViewModelHolder<M> {
            val viewModelContainer: ViewModelHolder<M> = ViewModelHolder()
            viewModelContainer.viewModel = viewModel
            return viewModelContainer
        }
    }
}