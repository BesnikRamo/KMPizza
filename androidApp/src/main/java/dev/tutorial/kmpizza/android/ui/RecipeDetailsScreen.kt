import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import dev.tutorial.kmpizza.android.R
import dev.tutorial.kmpizza.model.Ingredient
import dev.tutorial.kmpizza.model.Instruction
import dev.tutorial.kmpizza.android.ui.utils.TopBar
import dev.tutorial.kmpizza.model.ImageFile
import dev.tutorial.kmpizza.viewmodel.RecipeDetailsViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
public fun RecipeDetailsScreen(recipeId: Long? = null, upPress: () -> Unit) {
    val viewModel = remember { RecipeDetailsViewModel(recipeId) }
    val recipe by viewModel.recipe.collectAsState()

    val scrolling = Modifier
        .verticalScroll(rememberScrollState())

//    val upload by viewModel.upload.collectAsState()

//    if (upload) {
//        upPress()
//        viewModel.resetUpload()
//    }

//    val getImage = registerForGalleryResult(viewModel::onImageChanged)

    val placeholder = "https://m.media-amazon.com/images/I/413qxEF0QPL._AC_.jpg"


    Scaffold(
        topBar = { TopBar(upPress = upPress) }
    )
    { padding ->
        Column(
            modifier = scrolling.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            recipe?.images?.let {
                if (it.isNotEmpty()) {
                    HeaderImage(it[0].image, padding)
                }
//                else {
//                    PlaceholderImage(padding, getImage, recipeId == null)
//                }
            }
            recipeId?.let {
                recipe?.title?.let { Title(it) }
                SectionHeader(title = "Ingredients")
                recipe?.ingredients?.let { Ingredients(it) }
                SectionHeader(title = "Instructions")
                recipe?.instructions?.let { Instructions(it) }
            } ?: run {
                EditTitle(changeListener = viewModel, title = recipe?.title)
                SectionHeader(title = "Ingredients")
                EditIngredients(changeListener = viewModel, items = recipe?.ingredients)
                SectionHeader(title = "Instructions")
                EditInstructions(changeListener = viewModel, items = recipe?.instructions)
            }
            if (recipeId == null) {
                SubmitButton {
                    viewModel.saveRecipe()
                }
            }
        }

    }
}

//@Composable
//fun registerForGalleryResult(callback: (ImageFile) -> Unit) =
//    (LocalContext.current as AppCompatActivity).contentResolver.let { contentResolver ->
//        rememberLauncherForActivityResult (ActivityResultContracts.GetContent()) { uri: Uri? ->
//            uri?.toImageFile(contentResolver)?.let(callback)
//        }
//    }


@Composable
fun SubmitButton(
    onClick: () -> Unit
) {
    ExtendedFloatingActionButton(
        text = { Text(text = stringResource(id = R.string.save)) },
        shape = RoundedCornerShape(51),
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    )
}

@Composable
private fun EditInstructions(changeListener: RecipeDetailsViewModel, items: List<Instruction>?) {
    var isEdited by remember { mutableStateOf(false) }
    var description by remember { mutableStateOf("") }

    val onDescriptionChanged = { value: String ->
        description = value
    }

    val onInstructionAdded = {
        isEdited = true
        if (description.isNotEmpty()) {
            changeListener.onInstructionsChanged(
                Instruction(
                    order = items?.size?.plus(1) ?: 1,
                    description = description
                )
            )
            description = ""
        }
    }

    Instructions(items = items)
    if (isEdited) {
        NewInstruction(description = description, onDescriptionChanged = onDescriptionChanged)
    }
    AddItemButton(onAddInstruction = onInstructionAdded)
}

@Composable
private fun EditIngredients(changeListener: RecipeDetailsViewModel, items: List<Ingredient>?) {
    var isEdited by remember { mutableStateOf(false) }

    var name by remember { mutableStateOf("") }
    val onNameChanged = { value: String ->
        name = value
    }

    var amount by remember { mutableStateOf("") }
    val onAmountChanged = { value: String ->
        amount = value
    }

    var metric by remember { mutableStateOf("") }
    val onMetricChanged = { value: String ->
        metric = value
    }

    val onInstructionAdded = {
        isEdited = true
        if (name.isNotEmpty() && amount.isNotEmpty() && metric.isNotEmpty()) {
            changeListener.onIngredientsChanged(
                Ingredient(
                    name = name,
                    amount = amount.toDouble(),
                    metric = metric
                )
            )
            name = ""
            amount = ""
            metric = ""
        }
    }

    Ingredients(items = items)
    if (isEdited) {
        NewIngredient(name, amount, metric, onNameChanged, onAmountChanged, onMetricChanged)
    }
    AddItemButton(onAddInstruction = onInstructionAdded)
}

@Composable
private fun EditTitle(changeListener: RecipeDetailsViewModel, title: String?) {
    val text = title ?: ""
    val onTitleChanged = { value: String ->
        changeListener.onTitleChanged(value)
    }

    Row(modifier = Modifier.fillMaxWidth()) {
        TextField(
            value = text,
            onValueChange = onTitleChanged,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            label = { Text(text = stringResource(id = R.string.title)) })
    }

}

@Composable
private fun NewInstruction(description: String, onDescriptionChanged: (String) -> Unit) {
    Row() {
        TextField(
            value = description,
            onValueChange = onDescriptionChanged,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            label = { Text(text = stringResource(id = R.string.description)) })
    }
}


@Composable
private fun NewIngredient(
    name: String,
    amount: String,
    metric: String,
    onNameChanged: (String) -> Unit,
    onAmountChanged: (String) -> Unit,
    onMetricChanged: (String) -> Unit
) {
    Row(horizontalArrangement = Arrangement.SpaceAround) {
        TextField(
            value = name,
            onValueChange = onNameChanged,
            modifier = Modifier
                .weight(2f)
                .padding(top = 8.dp, end = 8.dp),
            label = { Text(text = stringResource(id = R.string.name)) })
        TextField(
            value = amount,
            onValueChange = onAmountChanged,
            modifier = Modifier
                .weight(1f)
                .padding(top = 8.dp, end = 4.dp),
            label = { Text(text = stringResource(id = R.string.amount)) })
        TextField(
            value = metric,
            onValueChange = onMetricChanged,
            modifier = Modifier
                .weight(1f)
                .padding(top = 8.dp),
            label = { Text(text = stringResource(id = R.string.metric)) })
    }
}


@Composable
private fun AddItemButton(onAddInstruction: () -> Unit = {}) {
    IconButton(onClick = onAddInstruction) {
        Icon(
            painter = rememberAsyncImagePainter(R.drawable.ic_add),
            contentDescription = null,
            modifier = Modifier.clip(CircleShape)
        )
    }
}


@Composable
private fun HeaderImage(image: Any, padding: PaddingValues) {
    Column(modifier = Modifier.padding(padding)) {
        AsyncImage(
            model = image,
            modifier = Modifier.size(200.dp),
            contentDescription = null,
            error = painterResource(id = R.drawable.no_pizza)
        )
    }
}

@Composable
private fun Ingredients(items: List<Ingredient>?) {
    Column() {
        items?.forEach {
            IngredientItem(it.name, it.amount, it.metric)
        }
    }
}

@Composable
private fun Instructions(items: List<Instruction>?) {
    Column() {
        items?.forEach {
            InstructionItem(it.order, it.description)
        }
    }
}

@Composable
private fun Title(title: String) {
    Text(
        text = title
    )
}

@Composable
private fun IngredientItem(name: String, amount: Double, metric: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = name
        )
        Row {
            Text(text = amount.toString())
            Text(modifier = Modifier.padding(horizontal = 4.dp), text = metric)
        }
    }
}

@Composable
private fun InstructionItem(order: Int, description: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        Text(text = "$order.")
        Text(modifier = Modifier.padding(horizontal = 16.dp), text = description)
    }
}

@Composable
private fun SectionHeader(title: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = rememberAsyncImagePainter(R.drawable.ornament),
            modifier = Modifier.height(40.dp),
            contentDescription = null,
        )
        Text(text = title)
    }

}

@Composable
private fun PlaceholderImage(
    padding: PaddingValues,
    getImage: ActivityResultLauncher<String>,
    isEditable: Boolean
) {
    val placeholder = "https://m.media-amazon.com/images/I/413qxEF0QPL._AC_.jpg"
    Box(contentAlignment = Alignment.Center) {
        HeaderImage(image = placeholder, padding = padding)
        if (isEditable) {
            IconButton(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color.Cyan),
                onClick = { getImage.launchAsImageResult() }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "edit image",
                    modifier = Modifier
                        .size(64.dp),
                    tint = Color.Black
                )
            }
        }
    }
}

private fun ActivityResultLauncher<String>.launchAsImageResult() = launch("image/*")


