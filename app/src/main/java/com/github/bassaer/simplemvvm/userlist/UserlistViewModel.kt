package com.github.bassaer.simplemvvm.userlist

import android.util.Log
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableArrayList
import com.github.bassaer.simplemvvm.BR
import com.github.bassaer.simplemvvm.data.source.UserDataSource
import com.github.bassaer.simplemvvm.data.User
import com.github.bassaer.simplemvvm.data.source.local.UserRepository

class UserlistViewModel(private val userRepository: UserRepository): BaseObservable() {
    var userlist = ObservableArrayList<User>()

    var navigator: UserlistNavigator? = null

    fun addNewUser(name: String) {
        val user = User(name = name, count = 0)
        userlist.add(user)
        userRepository.createUser(user)
        notifyPropertyChanged(BR.empty)
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

    fun openGitHubRepoList() {
        navigator?.openGitHubRepoList()
    }

    fun deleteAllUser() {
        userRepository.deleteAllUser()
        userlist.clear()
        notifyPropertyChanged(BR.empty)
    }


    @Bindable
    fun isEmpty() = userlist.isEmpty()
}