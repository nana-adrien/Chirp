package empire.digiprem.com.core.domain.auth

import empire.digiprem.com.core.domain.util.DataError
import empire.digiprem.com.core.domain.util.EmptyResult
import empire.digiprem.com.core.domain.util.Result

interface AuthService {
    suspend  fun login(
        email:String,
        password:String
    ): Result<AuthInfo,DataError.Remote>

    suspend  fun register(
        email:String,
        username:String,
        password:String
    ): EmptyResult<DataError.Remote>

  suspend  fun resendVerificationEmail(
        email:String
    ): EmptyResult<DataError.Remote>

  suspend  fun verifyEmail(
        token:String
    ): EmptyResult<DataError.Remote>


}