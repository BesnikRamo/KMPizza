import io.ktor.application.*
import org.koin.dsl.module

internal fun Application.getKoinModule() = module {
    single<LocalSource> { LocalSourceImpl(this@getKoinModule) }
}