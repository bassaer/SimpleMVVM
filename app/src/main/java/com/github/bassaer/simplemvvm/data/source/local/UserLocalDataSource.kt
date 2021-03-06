package com.github.bassaer.simplemvvm.data.source.local

import com.github.bassaer.simplemvvm.data.source.UserDataSource
import com.github.bassaer.simplemvvm.data.source.UserDataSource.GerUserCallback
import com.github.bassaer.simplemvvm.data.source.UserDataSource.LoadUserCallback
import com.github.bassaer.simplemvvm.data.User
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

    override fun createUser(user: User) {
        GlobalScope.launch {
            userDao.create(user)
        }
    }

    override fun saveUser(user: User) {
        GlobalScope.launch {
            userDao.save(user)
        }
    }

    override fun deleteAllUser() {
        GlobalScope.launch {
            userDao.removeAll()
        }
    }

    companion object {
        private var INSTANCE: UserLocalDataSource? = null
        fun getInstance(userDao: UserDao): UserLocalDataSource {
            return INSTANCE ?: synchronized(
                UserLocalDataSource::class.java) {
                INSTANCE
                    ?: UserLocalDataSource(userDao).also {
                    INSTANCE = it
                }
            }
        }
    }
}