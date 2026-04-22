package empire.digiprem.com.chat.data.database

import androidx.sqlite.SQLiteException
import empire.digiprem.com.core.domain.util.DataError
import empire.digiprem.com.core.domain.util.Result

suspend  fun <T> safeDatabaseUpdate(update: suspend ()->T):Result<T,DataError.Local> {
    return try {
       Result.Success(update())
    }catch (e:SQLiteException){
        Result.Failure(DataError.Local.DISK_FULL)
    }
}