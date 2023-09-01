package com.example.pokemon.data.user

import kotlinx.coroutines.flow.Flow

interface UserRepositoryInterface {
    suspend fun update(user: User)

    fun getUser(): Flow<User>

    suspend fun insert(user: User)

    suspend fun isEmpty(): Boolean

}

class UserRepository(private val userDao: UserDao): UserRepositoryInterface {

    override suspend fun update(user: User) {
        userDao.update(user)
    }

    override fun getUser(): Flow<User> = userDao.getUser()

    override suspend fun insert(user: User) = userDao.insert(user)

    override suspend fun isEmpty() = userDao.isEmpty()

}