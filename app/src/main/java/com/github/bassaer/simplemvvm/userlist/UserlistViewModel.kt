package com.github.bassaer.simplemvvm.userlist

import android.util.Log
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableArrayList
import com.github.bassaer.simplemvvm.BR
import com.github.bassaer.simplemvvm.data.UserDataSource
import com.github.bassaer.simplemvvm.data.local.User
import com.github.bassaer.simplemvvm.data.local.UserRepository

class UserlistViewModel(private val userRepository: UserRepository): BaseObservable() {
    var userlist = ObservableArrayList<User>()

    var navigator: UserlistNavigator? = null

    fun addNewUser(name: String) {
        val user = User(name = name, count = 0)
        userlist.add(user)
        userRepository.saveUser(user)
    }

    fun onActivityDestroyed() {
        navigator = null
    }

    fun loadUserlist() {
        userRepository.getUsers(object : UserDataSource.LoadUserCallback {

            override fun onUserLoaded(users: List<User>) {
                userlist.clear()
                userlist.addAll(users)
                notifyPropertyChanged(BR.empty)
            }

            override fun onDataNotAvailable() {
                Log.d(javaClass.simpleName, "failed to load user list.")
            }
        })
    }

    @Bindable
    fun isEmpty() = userlist.isEmpty()
}