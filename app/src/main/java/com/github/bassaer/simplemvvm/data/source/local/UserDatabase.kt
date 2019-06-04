package com.github.bassaer.simplemvvm.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.github.bassaer.simplemvvm.data.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserDatabase: RoomDatabase(){
    abstract fun userDao(): UserDao

    companion object {
        private var INSTANCE: UserDatabase? = null
        private val lock = Any()

        fun getInstance(context: Context): UserDatabase {
            return INSTANCE ?:
            synchronized(lock) {
                INSTANCE ?: Room.databaseBuilder(context.applicationContext, UserDatabase::class.java, "User.db")
                        .allowMainThreadQueries()
                        .build()
                        .also {
                            INSTANCE = it
                        }
            }
        }
    }
}