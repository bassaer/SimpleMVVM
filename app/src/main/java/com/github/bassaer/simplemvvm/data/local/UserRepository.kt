package com.github.bassaer.simplemvvm.data.local

import com.github.bassaer.simplemvvm.data.local.User
import com.github.bassaer.simplemvvm.data.local.UserDao
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UserRepository(private val userDao: UserDao) {

    fun getUser(id: Int) {
        GlobalScope.launch {
            userDao.findById(id)
        }
    }

    fun save(user: User) {
        GlobalScope.launch {
            userDao.save(user)
        }
    }
}