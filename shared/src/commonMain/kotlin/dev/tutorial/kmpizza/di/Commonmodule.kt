import dev.tutorial.kmpizza.api.KtorApi
import dev.tutorial.kmpizza.api.KtorApiImpl
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module


fun initKoin(appDeclaration: KoinAppDeclaration)  = startKoin {
    modules(
            apiModule,
        repositoryModule,
        viewModelModule
    )
}

private val apiModule = module {
    single<KtorApi> { KtorApiImpl() }
    factory { RecipesApi(get()) }
}
private val viewModelModule = module {
    single{ RecipeViewModel() }
}

private val repositoryModule = module {
    factory { RecipeRemoteSource(get()) }
    single { RecipeRepository() }
}