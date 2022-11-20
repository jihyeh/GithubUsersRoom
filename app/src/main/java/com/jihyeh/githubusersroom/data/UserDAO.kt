package com.jihyeh.githubusersroom.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jihyeh.githubusersroom.model.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDAO {
    @Query("SELECT COUNT(id) FROM user_table")
    suspend fun getCount(): Int

    @Query("SELECT request_time FROM user_table LIMIT 1")
    suspend fun getTime(): Long

    // 데이터 변경사항을 관찰할 수 있도록 Flow 를 사용
    @Query("SELECT * FROM user_table")
    suspend fun getUsers(): List<UserEntity>

    // onConflict = OnConflictStrategy.IGNORE: 이미 목록에 있는 단어와 정확하게 같다면 새 단어를 무시
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(userEntity: UserEntity)

    // 모든 단어 삭제
    @Query("DELETE FROM user_table")
    suspend fun deleteAll()
}