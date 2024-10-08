import dev.tutorial.kmpizza.api.KtorApi
import dev.tutorial.kmpizza.model.RecipeRequest
import dev.tutorial.kmpizza.model.RecipeResponse

import io.ktor.client.request.*

class RecipesApi(private val ktorApi: KtorApi) : KtorApi by ktorApi {
    companion object {
        const val RECIPES_BASE_URL = "recipes"
    }

    suspend fun getPizza(): String {
        return client.get {
            apiUrl("pizza")
        }
    }

    suspend fun getRecipes(): List<RecipeResponse> {
        return client.get {
            apiUrl(RECIPES_BASE_URL)
        }
    }

    suspend fun getRecipe(id: Long): RecipeResponse {
        return client.get {
            apiUrl("$RECIPES_BASE_URL/$id")
        }
    }

    suspend fun postRecipe(recipeRequest: RecipeRequest): Long? {
        try {
            return client.post<Long> {
                json()
                apiUrl(RECIPES_BASE_URL)
                setBody(recipeRequest)
            }
        } catch (e: Exception) {
            return null
        }
    }
}