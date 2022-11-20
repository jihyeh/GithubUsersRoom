package com.jihyeh.githubusersroom.ui

import androidx.lifecycle.*
import com.jihyeh.githubusersroom.model.UserUi
import com.jihyeh.githubusersroom.data.GithubRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SearchUserViewModel(private val repository: GithubRepository): ViewModel() {

    private val _showToast = MutableLiveData<String>()
    val showToast: LiveData<String> = _showToast

    private val _users = MutableLiveData<List<UserUi>>()
    val users: LiveData<List<UserUi>> = _users

    fun getUsers() {
        viewModelScope.launch {
            repository.getUsers().collectLatest {
                _users.value = it
            }
        }
    }

    fun clearUsers() {
        viewModelScope.launch {
            repository.clearAll()
            _showToast.postValue("Clear All Database")
        }
    }
}

class SearchUserViewModelFactory(
    private val repository: GithubRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SearchUserViewModel::class.java)){
            return SearchUserViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}