package empire.digiprem.com.core.data.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import empire.digiprem.com.core.data.auth.createDataStore
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformCoreData: Module = module {
    single<HttpClientEngine> { Darwin.create() }
    single<DataStore<Preferences>> { createDataStore() }
}