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
    private var toDosState =
        mutableStateOf<RequestPlayground.Request>(RequestPlayground.Request.Loading)
    private var newToDo = mutableStateOf("")

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

    fun onToDoWriting(task: String): String {
        newToDo.value = task

        val res = newToDo.value
        return res
    }

    fun onRefresh(): RequestPlayground.Request {
        toDosState.value = RequestPlayground.Request.Loading

        viewModelScope.launch {
            val res = requestPlayground.getToDos()
            toDosState.value = res
        }

        return toDosState.value
    }

    fun onDelete(item: Int): RequestPlayground.Request {
        toDosState.value = RequestPlayground.Request.Loading

        viewModelScope.launch {
            val res = requestPlayground.removeToDo(item)
            toDosState.value = res
        }

        return toDosState.value
    }

    fun onAddToDo(): RequestPlayground.Request {
        toDosState.value = RequestPlayground.Request.Loading

        viewModelScope.launch {
            val res = requestPlayground.createToDo(newToDo.value)

            toDosState.value = res
        }

        newToDo.value = ""
        return toDosState.value
    }
}