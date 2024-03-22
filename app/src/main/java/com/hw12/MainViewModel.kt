package com.hw12


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val repository: MainRepository) : ViewModel() {

    private val _state = MutableStateFlow<State>(State.Waiting)
    val state = _state.asStateFlow()
    private val _searchResult = MutableLiveData<String>()
    val searchResult: LiveData<String> = _searchResult

    fun updateSearchText(text: String) {
        viewModelScope.launch {
            if (text.length > 2) {
                _state.value = State.Ready
            } else {
                _state.value = State.Waiting
            }

        }
    }

    fun searchData(text: String) {
        viewModelScope.launch {
            _state.value = State.Search
            val result = repository.getData(text)
            _searchResult.postValue(result)
            _state.value = State.Success
        }
    }
}
