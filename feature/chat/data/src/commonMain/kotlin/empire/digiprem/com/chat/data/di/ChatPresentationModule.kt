package empire.digiprem.com.chat.data.di

import empire.digiprem.com.chat.data.chat.KtorChatParticipantService
import empire.digiprem.com.chat.data.chat.KtorChatService
import empire.digiprem.com.chat.domain.chat.ChatParticipantService
import empire.digiprem.com.chat.domain.chat.ChatService
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val chatDataModule = module {
    singleOf(::KtorChatParticipantService) bind ChatParticipantService::class
    singleOf(::KtorChatService) bind ChatService::class
}