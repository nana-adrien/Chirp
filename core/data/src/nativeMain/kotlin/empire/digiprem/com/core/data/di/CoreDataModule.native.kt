package empire.digiprem.com.core.data.di

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import io.ktor.client.plugins.logging.LoggingFormat
import org.koin.dsl.module

actual val platformCoreData: org.koin.core.module.Module=module{
    single<HttpClientEngine>{ Darwin.create() }

}