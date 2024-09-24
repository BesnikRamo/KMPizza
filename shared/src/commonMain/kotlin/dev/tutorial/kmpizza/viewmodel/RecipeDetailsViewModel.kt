package dev.tutorial.kmpizza.viewmodel

import CoroutineViewModel
import RecipeRepository
import dev.tutorial.kmpizza.model.Ingredient
import dev.tutorial.kmpizza.model.Instruction
import dev.tutorial.kmpizza.model.RecipeUiModel
import dev.tutorial.kmpizza.model.toRecipeRequest
import dev.tutorial.kmpizza.model.toRecipeUiModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface EditRecipeChangeListener {
    fun onTitleChanged(title: String)
    fun onIngredientsChanged(ingredient: Ingredient)
    fun onInstructionsChanged(instruction: Instruction)
}


private val _upload = MutableStateFlow<Boolean>(false)
val upload: StateFlow<Boolean> = _upload

@Suppress("unused")
fun observeUpload(coroutineScope: CoroutineScope, onChange: (Boolean?) -> Unit) {
    upload.onEach {
        onChange(it)
    }.launchIn(coroutineScope)
}

fun resetUpload(){
    _upload.value = false
}


class RecipeDetailsViewModel(private val id: Long?) : CoroutineViewModel(), KoinComponent, EditRecipeChangeListener {
    private val recipeRepository: RecipeRepository by inject()

    private val _recipe = MutableStateFlow<RecipeUiModel?>(
        RecipeUiModel(
            title = "",
            ingredients = listOf(),
            instructions = listOf(),
            images = listOf()
        )
    )
    val recipe: StateFlow<RecipeUiModel?> = _recipe

    init {
        id?.let { getRecipe(it) }
    }

    fun getRecipe(id: Long) {
        coroutineScope.launch {
            _recipe.value = recipeRepository.getRecipe(id).toRecipeUiModel()
        }
    }


    @Suppress("unused")
    fun observeRecipe(onChange: (RecipeUiModel?) -> Unit) {
        recipe.onEach {
            onChange(it)
        }.launchIn(coroutineScope)
    }


    override fun onTitleChanged(title: String) {
        _recipe.value = _recipe.value?.copy(title = title)

    }

    override fun onIngredientsChanged(ingredient: Ingredient) {
        val ingredients = _recipe.value?.ingredients
        _recipe.value =
            _recipe.value?.copy(ingredients = ingredients?.plus(ingredient) ?: listOf(ingredient))

    }

    override fun onInstructionsChanged(instruction: Instruction) {
        val instructions = _recipe.value?.instructions
        _recipe.value = _recipe.value?.copy(
            instructions = instructions?.plus(instruction) ?: listOf(instruction)
        )
    }

    fun saveRecipe() {
        coroutineScope.launch {
            recipe.value?.let {
                if (it.title.isNotEmpty() && it.ingredients.isNotEmpty() && it.instructions.isNotEmpty()){
                    val result = recipeRepository.postRecipe(it.toRecipeRequest())
                    result?.let { _upload.value = true }
                }
            }
        }
    }
}