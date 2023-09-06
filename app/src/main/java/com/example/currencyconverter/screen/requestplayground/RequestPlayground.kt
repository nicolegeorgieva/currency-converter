package com.example.currencyconverter.screen.requestplayground

import com.example.currencyconverter.request.client
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import javax.inject.Inject

class RequestPlayground @Inject constructor() {
    @Serializable
    data class AddToDoRequest(
        val task: String
    )

    @Serializable
    data class ToDoResponse(
        val toDo: List<String>
    )

    @Serializable
    data class RemoveToDoRequest(
        val item: Int
    )

    suspend fun createToDo(task: String): Request {
        return withContext(Dispatchers.IO) {
            try {
                val response =
                    client.post("https://nicole-georgieva-ktor-f408de41c386.herokuapp.com/add-todo") {
                        contentType(ContentType.Application.Json)
                        setBody(AddToDoRequest(task))
                    }
                Request.Success(response.body<ToDoResponse>().toDo)
            } catch (e: Exception) {
                Request.Error
            }
        }
    }

    suspend fun getToDos(): Request {
        return withContext(Dispatchers.IO) {
            try {
                val response =
                    client.get("https://nicole-georgieva-ktor-f408de41c386.herokuapp.com/todo")

                val toDoResponse = response.body<ToDoResponse>()

                Request.Success(toDoResponse.toDo)
            } catch (e: Exception) {
                Request.Error
            }
        }
    }

    suspend fun removeToDo(item: Int): Request {
        return withContext(Dispatchers.IO) {
            try {
                val response =
                    client.post("https://nicole-georgieva-ktor-f408de41c386.herokuapp.com/remove-todo") {
                        contentType(ContentType.Application.Json)
                        setBody(RemoveToDoRequest(item))
                    }

                val toDoResponse = response.body<ToDoResponse>()

                Request.Success(toDoResponse.toDo)
            } catch (e: Exception) {
                getToDos()
            }
        }
    }

    sealed interface Request {
        data class Success(val data: List<String>) : Request
        object Error : Request
        object Loading : Request
    }
}