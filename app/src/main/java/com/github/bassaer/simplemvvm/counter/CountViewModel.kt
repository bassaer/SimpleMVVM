package com.github.bassaer.simplemvvm.counter

import androidx.databinding.ObservableInt

class CountViewModel {
    val count = ObservableInt()

    init {
        count.set(0)
    }

    fun countUp() {
        count.set(count.get()+1)
    }
}