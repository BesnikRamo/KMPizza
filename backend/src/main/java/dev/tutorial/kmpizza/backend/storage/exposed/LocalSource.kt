internal interface LocalSource {
    suspend fun getPizza(): String
    suspend fun addIngredient(recipeId: Long, ingredient: Ingredient): Long
    suspend fun addInstruction(recipeId: Long, instruction: Instruction): Long
    suspend fun addRecipe(recipe: Recipe): Long
    suspend fun getRecipes(): List<Recipe>
    // suspend fun getRecipe(recipeId: Long) : Recipe
}