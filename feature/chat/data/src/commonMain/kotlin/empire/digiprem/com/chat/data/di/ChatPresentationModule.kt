package empire.digiprem.com.chat.data.di

import empire.digiprem.com.chat.data.dto.KtorChatParticipantService
import empire.digiprem.com.chat.domain.chat.ChatParticipantService
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val chatDataModule = module {
    singleOf( ::KtorChatParticipantService) bind  ChatParticipantService::class
}