package com.example.currencyconverter.screen.requestplayground

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RequestPlaygroundViewModel @Inject constructor(
    private val requestPlayground: RequestPlayground
) : ViewModel() {
    private val toDosState =
        mutableStateOf<RequestPlayground.Request>(RequestPlayground.Request.Loading)
    private val newToDo = mutableStateOf("")

    fun getToDosState(): RequestPlayground.Request {
        viewModelScope.launch {
            val res = requestPlayground.getToDos()

            toDosState.value = res
        }

        return toDosState.value
    }

    fun getToDo(): String {
        return newToDo.value
    }

    fun onToDoWriting(task: String) {
        newToDo.value = task
    }

    fun onRefresh() {
        toDosState.value = RequestPlayground.Request.Loading

        viewModelScope.launch {
            val res = requestPlayground.getToDos()
            toDosState.value = res
        }
    }

    fun onDelete(item: Int) {
        toDosState.value = RequestPlayground.Request.Loading

        viewModelScope.launch {
            val res = requestPlayground.removeToDo(item)
            toDosState.value = res
        }
    }

    fun onAddToDo() {
        toDosState.value = RequestPlayground.Request.Loading

        viewModelScope.launch {
            val res = requestPlayground.createToDo(newToDo.value)

            toDosState.value = res
        }

        newToDo.value = ""
    }
}