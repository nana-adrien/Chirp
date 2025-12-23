package empire.digiprem.com.core.data.di

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformCoreData: Module= module {
    single<HttpClientEngine>{ OkHttp.create()}
}