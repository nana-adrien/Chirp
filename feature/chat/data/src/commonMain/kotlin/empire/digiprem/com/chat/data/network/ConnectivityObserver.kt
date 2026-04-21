
package empire.digiprem.com.chat.data.network

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

expect class ConnectivityObserver {

    val isConnected: Flow<Boolean>

}