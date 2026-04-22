package empire.digiprem.com.chat.data.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import empire.digiprem.com.chat.data.chat.KtorChatParticipantService
import empire.digiprem.com.chat.data.chat.KtorChatService
import empire.digiprem.com.chat.data.chat.OfflineFirstChatRepository
import empire.digiprem.com.chat.data.chat.WebSocketChatConnectionClient
import empire.digiprem.com.chat.data.lifecycle.AppLifecycleObserver
import empire.digiprem.com.chat.data.message.OfflineFirstMessageRepository
import empire.digiprem.com.chat.database.DatabaseFactory
import empire.digiprem.com.chat.domain.chat.ChatConnectionClient
import empire.digiprem.com.chat.domain.chat.ChatParticipantService
import empire.digiprem.com.chat.domain.chat.ChatRepository
import empire.digiprem.com.chat.domain.chat.ChatService
import empire.digiprem.com.chat.domain.message.MessageRepository
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformChatDataModule:Module
val chatDataModule = module {
    includes(platformChatDataModule)
    singleOf(::KtorChatParticipantService) bind ChatParticipantService::class
    singleOf(::KtorChatService) bind ChatService::class
    singleOf(::OfflineFirstChatRepository) bind ChatRepository::class
    singleOf(::OfflineFirstMessageRepository) bind MessageRepository::class
    singleOf(::WebSocketChatConnectionClient) bind ChatConnectionClient::class
    single {
        Json {
            ignoreUnknownKeys=true
        }
    }
    single {
        get<DatabaseFactory>().create().setDriver(BundledSQLiteDriver()).build()
    }
}