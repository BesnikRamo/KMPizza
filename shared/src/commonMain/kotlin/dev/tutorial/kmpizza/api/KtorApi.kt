package dev.tutorial.kmpizza.api

import dev.tutorial.kmpizza.model.RecipeRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder

interface KtorApi {
    val client: HttpClient
    fun HttpRequestBuilder.apiUrl(path: String)
    fun HttpRequestBuilder.json()
    fun setBody(recipeRequest: RecipeRequest)
}