package com.github.bassaer.simplemvvm.data.source.local

import com.github.bassaer.simplemvvm.data.source.UserDataSource
import com.github.bassaer.simplemvvm.data.source.UserDataSource.GerUserCallback
import com.github.bassaer.simplemvvm.data.source.UserDataSource.LoadUserCallback
import com.github.bassaer.simplemvvm.data.User

class UserRepository(private val userLocalDataSource: UserLocalDataSource):
    UserDataSource {

    override fun getUsers(callback: LoadUserCallback) {
        userLocalDataSource.getUsers(object : LoadUserCallback {
            override fun onUserLoaded(users: List<User>) {
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

    override fun createUser(user: User) {
        userLocalDataSource.createUser(user)
    }

    override fun saveUser(user: User) {
        userLocalDataSource.saveUser(user)
    }

    override fun deleteAllUser() {
        userLocalDataSource.deleteAllUser()
    }

    companion object {
        private var INSTANCE: UserRepository? = null

        fun getInstance(userLocalDataSource: UserLocalDataSource): UserRepository {
            return INSTANCE
                ?: UserRepository(userLocalDataSource).also {
                INSTANCE = it
            }
        }
    }

}