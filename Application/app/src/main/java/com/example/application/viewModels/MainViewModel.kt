package com.example.application.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.application.data.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import com.example.application.repositories.MainRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    private var _data: MutableStateFlow<List<UserInfo>> = MutableStateFlow(emptyList())
    val data: MutableStateFlow<List<UserInfo>> = _data

    private var _page: MutableStateFlow<Int> = MutableStateFlow(0)
    private var _query: MutableStateFlow<String?> = MutableStateFlow(null)


    fun getData(): List<String> {
        return repository.getData()
    }

    fun getSearchResults(query: String) {

        if(query != _query.value) {
            _query.value = query
            _page.value = 0
            _data.value = emptyList()
        }

        viewModelScope.launch {
            try {
                val page = _page.value + 1
                println(query)
                val results = repository.getItems(query, page)

                val currentResults: MutableList<UserInfo> = _data.value.toMutableList()
                currentResults.addAll(results)

                _data.emit(currentResults)
                _page.emit(page)
            } catch (e:Throwable){
                e.message
            }
        }
    }
}