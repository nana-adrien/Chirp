package empire.digiprem.com.chat.data.lifecycle

import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import platform.UIKit.UIApplicationDidBecomeActiveNotification
import platform.UIKit.UIApplicationDidEnterBackgroundNotification
import platform.UIKit.UIApplicationState
import platform.UIKit.UIApplicationWillEnterForegroundNotification
import platform.UIKit.UIApplicationWillResignActiveNotification

actual class AppLifecycleObserver {
    actual val isInForeground: Flow<Boolean> = callbackFlow {

        val currentState=UIApplication.sharedApplication.applicationState
        val isCurrentlyInForeground=when(currentState){
            UIApplicationState.UIApplicationStateActive->true

            // App itself is active , but could be tha notification center is dragged down
            // or there's an ongoing phone call
            UIApplicationState.UIApplicationStateInactive->true
            else -> false
        }
        send(isCurrentlyInForeground)
        val notificationCenter=NsNotificaitonCenter.defaultCenter

        val foregroundObserver=notificationCenter.addObserverFromName(
            name= UIApplicationDidBecomeActiveNotification,
            `object`=null,
            queue=NSOperationQueue.mainQueue
        ){
            trySend(true)
        }
        val willEnterForegroundObserver=notificationCenter.addObserverFromName(
            name= UIApplicationWillEnterForegroundNotification,
            `object`=null,
            queue=NSOperationQueue.mainQueue
        ){
            trySend(true)
        }
        val backgroundObserver=notificationCenter.addObserverFromName(
            name= UIApplicationDidEnterBackgroundNotification,
            `object`=null,
            queue=NSOperationQueue.mainQueue
        ){
            trySend(true)
        }
        val willResignActiveObserver=notificationCenter.addObserverFromName(
            name= UIApplicationWillResignActiveNotification,
            `object`=null,
            queue=NSOperationQueue.mainQueue
        ){
            trySend(true)
        }
        awaitClose{
            notificaitonCenter.removeObserver(foregroundObserver)
            notificaitonCenter.removeObserver(willEnterForegroundObserver)
            notificaitonCenter.removeObserver(backgroundObserver)
            notificaitonCenter.removeObserver(willResignActiveObserver)
        }
    }

}