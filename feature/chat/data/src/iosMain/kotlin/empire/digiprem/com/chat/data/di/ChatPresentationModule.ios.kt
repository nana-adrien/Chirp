package empire.digiprem.com.chat.data.di

import empire.digiprem.com.chat.data.lifecycle.AppLifecycleObserver
import empire.digiprem.com.chat.data.network.ConnectivityObserver
import empire.digiprem.com.chat.database.DatabaseFactory
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val platformChatDataModule= module {
    single { DatabaseFactory() }
    singleOf(::AppLifecycleObserver)
    singleOf(::ConnectivityObserver)
}