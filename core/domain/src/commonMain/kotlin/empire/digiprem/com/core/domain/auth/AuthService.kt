package empire.digiprem.com.core.domain.auth

import empire.digiprem.com.core.domain.util.DataError
import empire.digiprem.com.core.domain.util.EmptyResult

interface AuthService {
  suspend  fun register(
        email:String,
        username:String,
        password:String
    ): EmptyResult<DataError.Remote>

  suspend  fun resendVerificationEmail(
        email:String
    ): EmptyResult<DataError.Remote>


}