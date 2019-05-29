package com.github.bassaer.simplemvvm.counter

import android.util.Log
import androidx.databinding.BaseObservable
import androidx.databinding.ObservableInt
import com.github.bassaer.simplemvvm.data.UserDataSource
import com.github.bassaer.simplemvvm.data.local.User
import com.github.bassaer.simplemvvm.data.local.UserRepository

class CountViewModel(private val userRepository: UserRepository): BaseObservable(){
    var user: User? = null
    val count = ObservableInt()
    val navigator: CountNavigator? = null

    fun loadUser(userId: String) {
        userRepository.getUser(userId, object : UserDataSource.GerUserCallback {

            override fun onUserLoaded(loadedUser: User) {
                user = loadedUser
                count.set(loadedUser.count)
            }

            override fun onDataNotAvailable() {
                Log.d(javaClass.simpleName, "Failed to get User count.")
            }

        })
    }

    fun saveCount() {
        user?.let {
            it.count = count.get()
            userRepository.saveUser(it)
        }
    }

    fun countUp() {
        count.set(count.get()+1)
    }

    fun reset() {
        user?.let {
            it.count = 0
        }
        count.set(0)
    }

}