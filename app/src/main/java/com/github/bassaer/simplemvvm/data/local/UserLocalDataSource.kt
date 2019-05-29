package com.github.bassaer.simplemvvm.data.local

import com.github.bassaer.simplemvvm.data.UserDataSource
import com.github.bassaer.simplemvvm.data.UserDataSource.GerUserCallback
import com.github.bassaer.simplemvvm.data.UserDataSource.LoadUserCallback
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UserLocalDataSource(private val userDao: UserDao): UserDataSource {

    override fun getUsers(callback: LoadUserCallback) {
        GlobalScope.launch {
            val users = userDao.findAll()
            if (users.isEmpty()) {
                callback.onDataNotAvailable()
            } else {
                callback.onUserLoaded(users)
            }
        }

    }

    override fun getUser(id: String, callback: GerUserCallback) {
        GlobalScope.launch {
            val user = userDao.findById(id)
            if (user == null) {
                callback.onDataNotAvailable()
            } else {
                callback.onUserLoaded(user)
            }
        }
    }

    override fun saveUser(user: User) {
        GlobalScope.launch {
            userDao.save(user)
        }
    }

    companion object {
        private var INSTANCE: UserLocalDataSource? = null
        fun getInstance(userDao: UserDao): UserLocalDataSource {
            if (INSTANCE == null) {
                synchronized(UserLocalDataSource::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = UserLocalDataSource(userDao)
                    }
                }
            }
            return INSTANCE!!
        }
    }
}