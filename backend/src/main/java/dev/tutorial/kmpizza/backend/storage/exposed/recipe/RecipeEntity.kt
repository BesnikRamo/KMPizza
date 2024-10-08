package dev.tutorial.kmpizza.backend.storage.exposed.recipe

import IngredientEntity
import IngredientTable
import InstructionEntity
import InstructionTable
import RecipeImageEntity
import RecipeImageTable
import dev.tutorial.kmpizza.model.RecipeRequest
import dev.tutorial.kmpizza.model.RecipeResponse
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import toIngredient
import toInstruction
import toRecipeImage

class RecipeEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<RecipeEntity>(RecipeTable)

    var title by RecipeTable.title
    val ingredients by IngredientEntity referrersOn IngredientTable.recipe
    val instructions by InstructionEntity referrersOn InstructionTable.recipe
    val recipeImages by RecipeImageEntity referrersOn RecipeImageTable.recipe
}

fun RecipeEntity.toRecipe() = RecipeRequest(
    id.value.toLong(),
    title,
    ingredients.map{it.toIngredient()},
            instructions.map{it.toInstruction()}
)


fun RecipeEntity.toRecipeResponse() = RecipeResponse(
    id.value.toLong(),
    title,
    ingredients.map { it.toIngredient() },
    instructions.map { it.toInstruction() },
    recipeImages.map { it.toRecipeImage() })
