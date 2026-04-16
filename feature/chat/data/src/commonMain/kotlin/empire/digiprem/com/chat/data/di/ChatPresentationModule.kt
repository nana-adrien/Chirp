package empire.digiprem.com.chat.data.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import empire.digiprem.com.chat.data.chat.KtorChatParticipantService
import empire.digiprem.com.chat.data.chat.KtorChatService
import empire.digiprem.com.chat.database.DatabaseFactory
import empire.digiprem.com.chat.domain.chat.ChatParticipantService
import empire.digiprem.com.chat.domain.chat.ChatService
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformChatDataModule:Module
val chatDataModule = module {
    includes(platformChatDataModule)
    singleOf(::KtorChatParticipantService) bind ChatParticipantService::class
    singleOf(::KtorChatService) bind ChatService::class
    single {
        get<DatabaseFactory>().create().setDriver(BundledSQLiteDriver()).build()
    }
}