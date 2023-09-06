package com.example.currencyconverter.screen.requestplayground

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.request.Request
import com.example.currencyconverter.request.createToDo
import com.example.currencyconverter.request.getToDos
import com.example.currencyconverter.request.removeToDo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RequestPlaygroundViewModel @Inject constructor(
    private val requestPlayground: RequestPlayground
) : ViewModel() {
    private var toDosState = mutableStateOf<Request>(Request.Loading)
    private var newToDo = mutableStateOf("")

    fun getToDosState(): Request {
        viewModelScope.launch {
            val res = getToDos()

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

    fun onRefresh(): Request {
        toDosState.value = Request.Loading

        viewModelScope.launch {
            val res = getToDos()
            toDosState.value = res
        }

        return toDosState.value
    }

    fun onDelete(item: Int): Request {
        toDosState.value = Request.Loading

        viewModelScope.launch {
            val res = removeToDo(item)
            toDosState.value = res
        }

        return toDosState.value
    }

    fun onAddToDo(): Request {
        toDosState.value = Request.Loading

        viewModelScope.launch {
            val res = createToDo(newToDo.value)

            toDosState.value = res
        }

        return toDosState.value
    }
}