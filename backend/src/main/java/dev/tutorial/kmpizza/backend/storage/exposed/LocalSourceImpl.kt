import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.coroutines.CoroutineContext

@OptIn(ObsoleteCoroutinesApi::class)
internal class LocalSourceImpl(application: io.ktor.application.Application) : LocalSource {
    private val dispatcher: CoroutineContext


    init {
        /*val config = application.environment.config.config("database")

        val url = System.getenv("JDBC_DATABASE_URL")
        val driver = config.property("driver").getString()
        val poolSize = config.property("poolSize").getString().toInt()
       // val driver = System.getenv("DRIVER")
       // val poolSize = System.getenv("POOL_SIZE")
        application.log.info("Connecting to db at $url")*/

        //TODO  perkohesisht hardcoded, kaloji tek application.conf
        val url = "jdbc:postgresql://localhost:5432/recipecollection"
        val driver = "org.postgresql.Driver"
        val poolSize = "20"
        val password1 = "postgres"
        val username1 = "postgres"

        println("JDBC URL: $url")
        println("Driver: $driver")
        println("Pool Size: $poolSize")

        dispatcher = newFixedThreadPoolContext(poolSize.toInt(), "database-pool")
        val hikariConfig = HikariConfig().apply {
            jdbcUrl = url
            maximumPoolSize = poolSize.toInt()
            driverClassName = driver
            password = password1
            username = username1
            validate()
        }

        Database.connect(HikariDataSource(hikariConfig))

        transaction {
            SchemaUtils.createMissingTablesAndColumns(
                RecipeTable,
                IngredientTable,
                InstructionTable
            )
        }
    }

    override suspend fun getPizza(): String = withContext(dispatcher) {
        "Pizza!"
    }

    override suspend fun addIngredient(recipeId: Long, ingredient: Ingredient) =
        withContext(dispatcher) {
            transaction {
                val recipe = RecipeEntity[recipeId.toInt()]
                IngredientEntity.new {
                    name = ingredient.name
                    amount = ingredient.amount.toBigDecimal()
                    metric = ingredient.metric
                    this.recipe = recipe
                }.id.value.toLong()
            }
        }

    override suspend fun addInstruction(recipeId: Long, instruction: Instruction) =
        withContext(dispatcher) {
            transaction {
                val recipe = RecipeEntity[recipeId.toInt()]
                InstructionEntity.new {
                    order = instruction.order
                    description = instruction.description
                    this.recipe = recipe
                }.id.value.toLong()
            }
        }

    override suspend fun addRecipe(recipe: Recipe) = withContext(dispatcher) {
        withContext(dispatcher) {
            val recipeId = transaction {
                RecipeEntity.new {
                    title = recipe.title
                }.id.value.toLong()
            }

            recipe.ingredients.forEach {
                addIngredient(recipeId, it)
            }

            recipe.instructions.forEach {
                addInstruction(recipeId, it)
            }
            recipeId
        }
    }

    override suspend fun getRecipes(): List<Recipe> = withContext(dispatcher) {
        transaction {
            RecipeEntity.all().map { it -> it.toRecipe() }
        }
    }

    /*  override suspend fun getRecipe(recipeId: Long): Recipe = withContext(dispatcher) {
          transaction {
              RecipeEntity[recipeId.toInt()].toRecipeResponse()
          }
      }*/
}