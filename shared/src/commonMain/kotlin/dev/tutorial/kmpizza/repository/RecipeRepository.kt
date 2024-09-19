import dev.tutorial.kmpizza.model.Recipe
import dev.tutorial.kmpizza.model.RecipeResponse
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RecipeRepository : KoinComponent {
    private val recipeRemoteSource: RecipeRemoteSource by inject()

    suspend fun postRecipe(recipe: Recipe): Long = recipeRemoteSource.postRecipe(recipe)
    suspend fun getRecipes(): List<RecipeResponse> = recipeRemoteSource.getRecipes()
}