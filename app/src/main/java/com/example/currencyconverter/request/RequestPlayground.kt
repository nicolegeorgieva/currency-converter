package com.example.currencyconverter.request

import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable

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

suspend fun createToDo(task: String): List<String> {
    return withContext(Dispatchers.IO) {
        val response =
            client.post("https://nicole-georgieva-ktor-f408de41c386.herokuapp.com/add-todo") {
                contentType(ContentType.Application.Json)
                setBody(AddToDoRequest(task))
            }

        response.body<ToDoResponse>().toDo
    }
}

suspend fun getToDos(): List<String> {
    return withContext(Dispatchers.IO) {
        val response =
            client.get("https://nicole-georgieva-ktor-f408de41c386.herokuapp.com/todo")

        val toDoResponse = response.body<ToDoResponse>()

        toDoResponse.toDo
    }
}

suspend fun removeToDo(item: Int): List<String> {
    return withContext(Dispatchers.IO) {
        val response =
            client.post("https://nicole-georgieva-ktor-f408de41c386.herokuapp.com/remove-todo") {
                contentType(ContentType.Application.Json)
                setBody(RemoveToDoRequest(item))
            }

        val toDoResponse = response.body<ToDoResponse>()

        toDoResponse.toDo
    }
}