package com.github.bassaer.simplemvvm.data.local

import android.util.Log
import com.github.bassaer.simplemvvm.data.UserDataSource
import com.github.bassaer.simplemvvm.data.UserDataSource.GerUserCallback
import com.github.bassaer.simplemvvm.data.UserDataSource.LoadUserCallback

class UserRepository(private val userLocalDataSource: UserLocalDataSource): UserDataSource {

    override fun getUsers(callback: LoadUserCallback) {
        Log.d(javaClass.simpleName, "getUsers in UserRepository")
        userLocalDataSource.getUsers(object : LoadUserCallback {
            override fun onUserLoaded(users: List<User>) {
                Log.d(javaClass.simpleName, "getUsers in UserRepository callback")
                for (user in users) {
                    println("getUsers in UserRepository callback!! ${user.name}")
                }
                callback.onUserLoaded(users)
            }

            override fun onDataNotAvailable() {
                callback.onDataNotAvailable()
            }
        })
    }

    override fun getUser(id: String, callback: GerUserCallback) {
        userLocalDataSource.getUser(id, object : GerUserCallback {
            override fun onUserLoaded(user: User) {
                callback.onUserLoaded(user)
            }

            override fun onDataNotAvailable() {
                callback.onDataNotAvailable()
            }
        })
    }

    override fun saveUser(user: User) {
        userLocalDataSource.saveUser(user)
    }

    companion object {
        private var INSTANCE: UserRepository? = null

        fun getInstance(userLocalDataSource: UserLocalDataSource): UserRepository {
            if (INSTANCE == null) {
                INSTANCE = UserRepository(userLocalDataSource)
            }
            return INSTANCE!!
        }
    }

}