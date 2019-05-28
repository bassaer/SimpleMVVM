package com.github.bassaer.simplemvvm.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {
    @Insert(onConflict = REPLACE)
    suspend fun create(user: User)
    @Update
    suspend fun save(user: User)
    @Query("SELECT * FROM user WHERE id = :userId")
    suspend fun findById(userId: String): User?
    @Query("SELECT * FROM user")
    suspend fun findAll(): List<User>
    @Query("DELETE FROM user")
    suspend fun removeAll()
}