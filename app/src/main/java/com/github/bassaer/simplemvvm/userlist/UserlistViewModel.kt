package com.github.bassaer.simplemvvm.userlist

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import com.github.bassaer.simplemvvm.data.local.User
import com.github.bassaer.simplemvvm.data.local.UserRepository

class UserlistViewModel(private val userRepository: UserRepository): BaseObservable() {
    var users = ObservableArrayList<User>()
    val name = ObservableField<String>()
    val count = ObservableField<Int>()

    var navigator: UserlistNavigator? = null

    fun loadUserlist() {

    }

    fun addNewUser(name: String) {
        userRepository.addUser(name)
    }

    fun onActivityDestroyed() {
        navigator = null
    }

    @Bindable
    fun isEmpty() = users.isEmpty()
}