package com.example.currencyconverter.screen.requestplayground

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.ComposeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RequestPlaygroundViewModel @Inject constructor(
    private val requestPlayground: RequestPlayground
) : ComposeViewModel<RequestUi, RequestPlaygroundEvent>() {
    private val toDosState =
        mutableStateOf<RequestPlayground.Request>(RequestPlayground.Request.Loading)
    private val newToDo = mutableStateOf("")

    @Composable
    override fun uiState(): RequestUi {
        return RequestUi(
            tasks = getToDosState(),
            currentTask = getToDo()
        )
    }

    @Composable
    private fun getToDosState(): RequestPlayground.Request {
        return toDosState.value
    }

    @Composable
    private fun getToDo(): String {
        return newToDo.value
    }

    override fun onEvent(event: RequestPlaygroundEvent) {
        when (event) {
            RequestPlaygroundEvent.OnAddToDo -> onAddToDo()
            is RequestPlaygroundEvent.OnDelete -> onDelete(event.item)
            RequestPlaygroundEvent.OnStart -> onStart()
            is RequestPlaygroundEvent.OnToDoWriting -> onToDoWriting(event.task)
        }
    }

    private fun onStart() {
        viewModelScope.launch {
            toDosState.value = RequestPlayground.Request.Loading

            val res = requestPlayground.getToDos()
            toDosState.value = res
        }
    }

    private fun onToDoWriting(task: String) {
        newToDo.value = task
    }

    private fun onDelete(item: Int) {
        toDosState.value = RequestPlayground.Request.Loading

        viewModelScope.launch {
            val res = requestPlayground.removeToDo(item)
            toDosState.value = res
        }
    }

    private fun onAddToDo() {
        toDosState.value = RequestPlayground.Request.Loading

        viewModelScope.launch {
            val res = requestPlayground.createToDo(newToDo.value)

            toDosState.value = res
        }

        newToDo.value = ""
    }
}