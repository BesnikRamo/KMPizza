import dev.tutorial.kmpizza.model.RecipeRequest
import dev.tutorial.kmpizza.model.RecipeResponse
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RecipeRepository : KoinComponent {
    private val recipeRemoteSource: RecipeRemoteSource by inject()

    suspend fun postRecipe(recipe: RecipeRequest): Long? = recipeRemoteSource.postRecipe(recipe)
    suspend fun getRecipes(): List<RecipeResponse> = recipeRemoteSource.getRecipes()
    suspend fun getRecipe(id: Long) : RecipeResponse = recipeRemoteSource.getRecipe(id)
}