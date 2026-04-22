package empire.digiprem.com.chirp.di

import empire.digiprem.com.chirp.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val  MainModule = module {
    viewModelOf( ::MainViewModel )
    single {
        CoroutineScope(SupervisorJob()+ Dispatchers.Default)
    }
}