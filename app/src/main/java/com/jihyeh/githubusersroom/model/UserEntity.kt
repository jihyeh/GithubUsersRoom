package com.jihyeh.githubusersroom.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
class UserEntity(
    @ColumnInfo(name = "request_time")
    val time: Long,
    @ColumnInfo(name = "login")
    val login: String,
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "html_url")
    val html_url: String,
    @ColumnInfo(name = "score")
    val score: Float
)