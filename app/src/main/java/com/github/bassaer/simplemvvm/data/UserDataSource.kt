package com.github.bassaer.simplemvvm.data

import com.github.bassaer.simplemvvm.data.local.User

interface UserDataSource {
    interface LoadUserCallback {
        fun onUserLoaded(users: List<User>)
        fun onDataNotAvailable()
    }
    interface GerUserCallback {
        fun onUserLoaded(user: User)
        fun onDataNotAvailable()
    }

    fun getUsers(callback: LoadUserCallback)
    fun getUser(id: String, callback: GerUserCallback)
    fun createUser(user: User)
    fun saveUser(user: User)
}