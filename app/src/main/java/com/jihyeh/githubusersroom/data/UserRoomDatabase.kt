package com.jihyeh.githubusersroom.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jihyeh.githubusersroom.model.UserEntity

// exportSchema : 빌드 경고를 피하기 위해 false
@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class UserRoomDatabase: RoomDatabase() {

    abstract fun userDao(): UserDAO

    companion object {
        @Volatile
        private var INSTANCE: UserRoomDatabase? = null

        fun getDatabase(context: Context): UserRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserRoomDatabase::class.java,
                    "user_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}