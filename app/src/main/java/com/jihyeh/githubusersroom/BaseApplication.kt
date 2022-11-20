package com.jihyeh.githubusersroom

import android.app.Application
import com.jihyeh.githubusersroom.data.UserRoomDatabase

class BaseApplication : Application() {
    val database by lazy { UserRoomDatabase.getDatabase(this) }
}