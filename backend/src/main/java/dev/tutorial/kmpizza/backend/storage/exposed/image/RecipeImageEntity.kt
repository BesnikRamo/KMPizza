import dev.tutorial.kmpizza.backend.storage.exposed.recipe.RecipeEntity
import dev.tutorial.kmpizza.model.RecipeImage
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class RecipeImageEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<RecipeImageEntity>(RecipeImageTable)

    var image by RecipeImageTable.image
    var recipe by RecipeEntity referencedOn RecipeImageTable.recipe
}

fun RecipeImageEntity.toRecipeImage() = RecipeImage(id.value.toLong(), image)