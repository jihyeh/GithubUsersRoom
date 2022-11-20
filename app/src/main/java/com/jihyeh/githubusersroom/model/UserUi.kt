package com.jihyeh.githubusersroom.model

data class UserUi(
    val login: String,
    val id: Long,
    val html_url: String,
    val score: Float
) {
    fun toEntity(time: Long): UserEntity {
        return UserEntity(
            time = time,
            login = this.login,
            id = this.id,
            html_url = this.html_url,
            score = this.score
        )
    }
}
