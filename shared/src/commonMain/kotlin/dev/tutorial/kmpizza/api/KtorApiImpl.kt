package dev.tutorial.kmpizza.api

import dev.tutorial.kmpizza.model.RecipeRequest
import io.ktor.client.*
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.*
import io.ktor.http.*


class KtorApiImpl() : KtorApi {

   // val prodUrl = "https://kmpizza.fly.dev/"
   val prodUrl = "http://10.0.2.2:9090/"

    override val client = HttpClient {
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
    }

    override fun HttpRequestBuilder.apiUrl(path: String) {
        url {
            takeFrom(prodUrl)
            encodedPath = path
        }
    }

    override fun HttpRequestBuilder.json() {
        contentType(ContentType.Application.Json)
    }

    override fun setBody(recipeRequest: RecipeRequest) {
        TODO("Not yet implemented")
    }
}