package empire.digiprem.com.core.presentation.di

import empire.digiprem.com.core.presentation.util.ScopedStoreRegistryViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val corePresentationModule= module {
    viewModelOf(::ScopedStoreRegistryViewModel)
}