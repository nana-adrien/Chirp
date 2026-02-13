package empire.digiprem.com.chirp.di

import empire.digiprem.com.chirp.MainViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val  MainModule = module {
    viewModelOf( ::MainViewModel )
}