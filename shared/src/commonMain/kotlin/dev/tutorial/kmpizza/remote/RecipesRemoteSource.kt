import dev.tutorial.kmpizza.model.Ingredient
import dev.tutorial.kmpizza.model.Instruction
import dev.tutorial.kmpizza.model.RecipeRequest

interface EditRecipeChangeListener {
    fun onTitleChanged(title: String)
    fun onIngredientsChanged(ingredient: Ingredient)
    fun onInstructionsChanged(instruction: Instruction)
}

class RecipeRemoteSource(
    private val recipesApi: RecipesApi
) {

    suspend fun getRecipes() = recipesApi.getRecipes()
    suspend fun postRecipe(recipe: RecipeRequest) = recipesApi.postRecipe(recipe)
    suspend fun getRecipe(id: Long) = recipesApi.getRecipe(id)
}
