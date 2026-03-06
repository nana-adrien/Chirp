package empire.digiprem.com.chat.presentation.di

import empire.digiprem.com.chat.presentation.chat_list_detail.ChatListDetailViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module


val  chatPresentationModule = module {
    viewModelOf(::ChatListDetailViewModel)
}