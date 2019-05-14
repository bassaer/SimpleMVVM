package com.github.bassaer.simplemvvm.userlist

import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import java.lang.ref.WeakReference

class UserViewModel {
    val id = ObservableInt()
    val name = ObservableField<String>()
    val count = ObservableInt()
    private var navigator: WeakReference<UserNavigator>? = null

    fun openCounter() {
        navigator?.get()?.openCounter(id.get())
    }
}