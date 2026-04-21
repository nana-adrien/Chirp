package empire.digiprem.com.chat.data.di

import empire.digiprem.com.chat.data.lifecycle.AppLifecycleObserver
import empire.digiprem.com.chat.database.DatabaseFactory
import empire.digiprem.com.chat.domain.chat.ChatRepository
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual val platformChatDataModule= module {
    single { DatabaseFactory(androidContext()) }
    singleOf(::AppLifecycleObserver)
}