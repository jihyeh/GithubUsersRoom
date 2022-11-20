package com.jihyeh.githubusersroom.model

data class SearchUsersResponse(
    val total_count: Int,
    val incomplete_results: Boolean,
    val items: List<UserUi>
)
