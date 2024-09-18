import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.material.Text
import androidx.compose.runtime.getValue

@Composable
public fun RecipesScreen() {
    val viewModel = remember {
        RecipeViewModel()
    }
    val recipes by viewModel.recipes.collectAsState()

    Recipes(items = recipes)
}

@Composable
fun Recipes(
    items: List<RecipeResponse>
) {
    LazyColumn {
        itemsIndexed(items = items,
            itemContent = { _, item ->
                Text(text = item.title)
            })

    }
}