package empire.digiprem.com.chirp.di

import empire.digiprem.com.auth.presentation.di.authPresentationModule
import empire.digiprem.com.core.data.di.coreDataModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module


fun initKoin(
    config: KoinAppDeclaration?=null
){
    startKoin {
        config?.invoke(this)
        modules(
            coreDataModule,
            authPresentationModule
        )
    }
}