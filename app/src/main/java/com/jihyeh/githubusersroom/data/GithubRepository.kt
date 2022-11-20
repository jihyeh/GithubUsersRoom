package com.jihyeh.githubusersroom.data

import com.jihyeh.githubusersroom.model.UserUi
import kotlinx.coroutines.flow.Flow

interface GithubRepository {
    suspend fun getUsers(): Flow<List<UserUi>>
    suspend fun clearAll()
}