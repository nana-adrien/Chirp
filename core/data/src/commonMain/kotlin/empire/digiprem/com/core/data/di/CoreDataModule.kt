package empire.digiprem.com.core.data.di

import empire.digiprem.com.core.data.auth.KtorAuthService
import empire.digiprem.com.core.data.logging.KermitLogger
import empire.digiprem.com.core.data.networking.HttpClientFactory
import empire.digiprem.com.core.domain.auth.AuthService
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformCoreData: Module

val coreDataModule = module{
    includes(platformCoreData)
    single<KermitLogger> { KermitLogger}
    single {
        HttpClientFactory(get()).create(get())
    }
    singleOf(::KtorAuthService) bind AuthService::class
}