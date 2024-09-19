import dev.tutorial.kmpizza.model.Recipe

class RecipeRemoteSource(
    private val recipesApi: RecipesApi
) {

    suspend fun getRecipes() = recipesApi.getRecipes()
    suspend fun postRecipe(recipe: Recipe) = recipesApi.postRecipe(recipe)
}