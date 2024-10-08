

object Versions {
    const val KTOR_VERSION = "1.6.7"
    const val SHADOW_JAR_VERSION = "7.1.1"
    const val EXPOSED_VERSION = "0.36.2"
    const val POSTGRESQL_VERSION = "42.3.1"
    const val HIKARI_VESRION = "5.0.0"
    const val KOIN_VERSION = "3.1.2"
    const val KOTLIN_VERSION = "1.9.0"
    const val LIFECYCLE_VERSION = "2.4.1"
    const val COMPOSE = "1.1.1"
    const val COMPOSE_ACT = "1.6.0-alpha01"
    const val COMPOSE_NAV = "2.5.0-alpha03"
    const val MATERIAL = "1.5.0"
    const val COIL = "2.5.0"

    object Jvm {
        const val KTOR_AUTH = "io.ktor:ktor-auth:$KTOR_VERSION"
        const val KTOR_WEB_SOCKETS = "io.ktor:ktor-websockets:$KTOR_VERSION"
        const val KTOR_CLIENT_APACHE = "io.ktor:ktor-client-apache:$KTOR_VERSION"
        const val KTOR_SERVER_NETTY = "io.ktor:ktor-server-netty:$KTOR_VERSION"
        const val KTOR_SERIALIZATION = "io.ktor:ktor-serialization:$KTOR_VERSION"
        const val JETBRAINS_EXPOSED_CORE = "org.jetbrains.exposed:exposed-core:$EXPOSED_VERSION"
        const val JETBRAINS_EXPOSED_DAO = "org.jetbrains.exposed:exposed-dao:$EXPOSED_VERSION"
        const val JETBRAINS_EXPOSED_JDBC = "org.jetbrains.exposed:exposed-jdbc:$EXPOSED_VERSION"
        const val HIKARI_CONNECTION_POOL = "com.zaxxer:HikariCP:$HIKARI_VESRION"
        const val POSTGRESQL = "org.postgresql:postgresql:$POSTGRESQL_VERSION"
        const val KOIN_KTOR = "io.insert-koin:koin-ktor:$KOIN_VERSION"

       // const val MATERIAL_COMPONENTS = "com.google.android.material:material:$MATERIAL"
    }

    object Common {
        const val KTOR_CLIENT_CORE = "io.ktor:ktor-client-core:$KTOR_VERSION"
        const val KTOR_CLIENT_JSON = "io.ktor:ktor-client-json:$KTOR_VERSION"
        const val KTOR_LOGGING = "io.ktor:ktor-client-logging:$KTOR_VERSION"
        const val KTOR_CLIENT_SERIALIZATION = "io.ktor:ktor-client-serialization:$KTOR_VERSION"
        const val KOIN_CORE = "io.insert-koin:koin-core:$KOIN_VERSION"
    }

    object Android {
        const val KTOR_CLIENT = "io.ktor:ktor-client-android:$KTOR_VERSION"
        const val KTOR_OKHTTP = "io.ktor:ktor-client-okhttp:$KTOR_VERSION"
        const val VIEW_MODEL = "androidx.lifecycle:lifecycle-viewmodel-ktx:$LIFECYCLE_VERSION"
        // Compose
        const val COMPOSE_UI = "androidx.compose.ui:ui:$COMPOSE"
        const val COMPOSE_GRAPHICS = "androidx.compose.ui:ui-graphics:$COMPOSE"
        const val COMPOSE_TOOLING = "androidx.compose.ui:ui-tooling:$COMPOSE"
        const val COMPOSE_FOUNDATION = "androidx.compose.foundation:foundation-layout:$COMPOSE"
        const val COMPOSE_MATERIAL = "androidx.compose.material:material:$COMPOSE"
        const val COMPOSE_NAVIGATION = "androidx.navigation:navigation-compose:$COMPOSE_NAV"
        const val COMPOSE_ACTIVITY = "androidx.activity:activity-compose:$COMPOSE_ACT"

        const val MATERIAL_COMPONENTS = "com.google.android.material:material:$MATERIAL"
        const val KOIN_ANDROID_MAIN = "io.insert-koin:koin-android:$KOIN_VERSION"

        const val COMPOSE_COIL = "io.coil-kt:coil-compose:$COIL"
    }
    object iOS {
        const val KTOR_CLIENT = "io.ktor:ktor-client-ios:$KTOR_VERSION"

    }
}