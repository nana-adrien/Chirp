package empire.digiprem.com.core.data.auth

import empire.digiprem.com.core.data.dto.request.RegisterRequest
import empire.digiprem.com.core.data.dto.request.ResendVerificationEmailRequest
import empire.digiprem.com.core.data.networking.get
import empire.digiprem.com.core.data.networking.post
import empire.digiprem.com.core.domain.auth.AuthService
import empire.digiprem.com.core.domain.util.DataError
import empire.digiprem.com.core.domain.util.EmptyResult
import io.ktor.client.HttpClient

class KtorAuthService(
    private val httpClient: HttpClient
): AuthService{
    override suspend fun register(
        email: String,
        username: String,
        password: String
    ): EmptyResult<DataError.Remote> {
        return httpClient.post(
            route ="/auth/register",
            body = RegisterRequest(
                email=email,
                username=username,
                password=password
            )
        )
    }
    override suspend fun resendVerificationEmail(email: String): EmptyResult<DataError.Remote> {
        return httpClient.post(
            route ="/auth/resend-verification",
            body = ResendVerificationEmailRequest(
                email = email,
            )
        )
    }
    override suspend fun verifyEmail(token: String): EmptyResult<DataError.Remote> {
        return httpClient.get (
            route ="/auth/verify",
            queryParams = mapOf("token" to token)
        )
    }
}