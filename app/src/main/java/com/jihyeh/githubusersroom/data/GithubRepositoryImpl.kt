package com.jihyeh.githubusersroom.data

import android.util.Log
import com.jihyeh.githubusersroom.model.UserUi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class GithubRepositoryImpl(private val userDAO: UserDAO): GithubRepository {
    private val service: GithubService = GithubService.create()

    override suspend fun getUsers(): Flow<List<UserUi>> {
        return flow {
            try {
                val callTime = System.currentTimeMillis()

                // 1. db 데이터가 있는지 체크
                // 2-1. 없을 경우 api 호출 & db 에 저장
                // 2-2. 있을 경우 time 비교
                // 3. 10초 전 일 경우 db 에서 리턴
                // 4. 10초 후 일 경우 api 호출 & db 전체 삭제 후 저장
                var isDeleteDB = false
                val columCount = userDAO.getCount()
                Log.d("hjh", "columCount: $columCount")
                if (columCount > 0) {  // 1. db 데이터가 있는지 체크
                    val savedTime = userDAO.getTime()
                    if (callTime - savedTime < 10000) { // 2-2. 있을 경우 time 비교
                        emit(userDAO.getUsers().map { it.toUi() }).also { Log.e("hjh", "10초 전, db 에서 리턴") }
                        return@flow
                    } else {
                        isDeleteDB = true
                    }
                }

                val response = service.getSearchUsers("google", 1, 30)
                if (isDeleteDB) {
                    // 4. 10초 후 일 경우 api 호출 & db 전체 삭제 후 저장
                    Log.e("hjh", "10초 후, api 호출 & db 전체 삭제 후 저장")
                    userDAO.deleteAll().also { Log.e("hjh", "deleteAll db complete") }
                } else {
                    // 2-1. 없을 경우 api 호출 & db 에 저장
                    Log.e("hjh", "없는 경우, api 호출 & db 에 저장")
                }
                response.items.insertDB(callTime)
                emit(response.items)

            } catch (exception: IOException) {
                emit(listOf())
            } catch (exception: HttpException) {
                emit(listOf())
            }
        }
    }

    override suspend fun clearAll() {
        userDAO.deleteAll().also { Log.e("hjh", "deleteAll db complete") }
    }

    private suspend fun List<UserUi>.insertDB(callTime: Long) {
        forEach {
            userDAO.insertUser(it.toEntity(callTime))
        }.also { Log.e("hjh", "insert db complete") }
    }
}