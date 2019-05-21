package com.github.bassaer.simplemvvm.data.local

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UserRepository(private val userDao: UserDao) {

    fun getUser(id: String) {
        GlobalScope.launch {
            userDao.findById(id)
        }
    }

    fun addUser(name: String) {
        GlobalScope.launch {
            userDao.create(User(name = name, count = 0))
        }
    }

    fun save(user: User) {
        GlobalScope.launch {
            userDao.save(user)
        }
    }

}