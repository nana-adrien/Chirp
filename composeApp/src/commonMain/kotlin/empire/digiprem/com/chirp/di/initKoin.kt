package empire.digiprem.com.chirp.di

import empire.digiprem.com.auth.presentation.di.authPresentationModule
import empire.digiprem.com.chat.data.di.chatDataModule
import empire.digiprem.com.chat.presentation.di.chatPresentationModule
import empire.digiprem.com.core.data.di.coreDataModule
import empire.digiprem.com.core.presentation.di.corePresentationModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration


fun initKoin(
    config: KoinAppDeclaration?=null
){
    startKoin {
        config?.invoke(this)
        modules(
            coreDataModule,
            authPresentationModule,
            corePresentationModule,
            chatPresentationModule,
            chatDataModule,
            MainModule
        )
    }
}