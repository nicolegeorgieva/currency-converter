package com.example.currencyconverter.request

import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.Serializable

@Serializable
data class AddToDoRequest(
    val task: String
)

@Serializable
data class ToDoResponse(
    val toDo: List<String>
)

suspend fun createToDo(task: String) {
    client.post("https://nicole-georgieva-ktor-f408de41c386.herokuapp.com/add-todo") {
        contentType(ContentType.Application.Json)
        setBody(AddToDoRequest(task))
    }
}

suspend fun getToDos(): List<String> {
    val response = client.get("https://nicole-georgieva-ktor-f408de41c386.herokuapp.com/todo") {}

    val toDoResponse = response.body<ToDoResponse>()

    return toDoResponse.toDo
}