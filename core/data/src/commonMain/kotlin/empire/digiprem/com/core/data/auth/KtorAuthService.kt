package empire.digiprem.com.core.data.auth

import empire.digiprem.com.core.data.dto.AuthInfoSerializable
import empire.digiprem.com.core.data.dto.request.LoginRequest
import empire.digiprem.com.core.data.dto.request.RegisterRequest
import empire.digiprem.com.core.data.dto.request.EmailRequest
import empire.digiprem.com.core.data.dto.request.ResetPasswordRequest
import empire.digiprem.com.core.data.mappers.toDomain
import empire.digiprem.com.core.data.networking.get
import empire.digiprem.com.core.data.networking.post
import empire.digiprem.com.core.domain.auth.AuthInfo
import empire.digiprem.com.core.domain.auth.AuthService
import empire.digiprem.com.core.domain.util.DataError
import empire.digiprem.com.core.domain.util.EmptyResult
import empire.digiprem.com.core.domain.util.Result
import empire.digiprem.com.core.domain.util.map
import io.ktor.client.HttpClient

class KtorAuthService(
    private val httpClient: HttpClient
) : AuthService {
    override suspend fun login(
        email: String,
        password: String
    ): Result<AuthInfo, DataError.Remote> {
        return httpClient.post<LoginRequest, AuthInfoSerializable>(
            route = "/auth/login",
            body = LoginRequest(
                email = email,
                password = password
            )
        ).map { authInfoSerializable ->
            authInfoSerializable.toDomain()
        }
    }

    override suspend fun register(
        email: String,
        username: String,
        password: String
    ): EmptyResult<DataError.Remote> {
        return httpClient.post(
            route = "/auth/register",
            body = RegisterRequest(
                email = email,
                username = username,
                password = password
            )
        )
    }

    override suspend fun resendVerificationEmail(email: String): EmptyResult<DataError.Remote> {
        return httpClient.post(
            route = "/auth/resend-verification",
            body = EmailRequest(
                email = email,
            )
        )
    }

    override suspend fun verifyEmail(token: String): EmptyResult<DataError.Remote> {
        return httpClient.get(
            route = "/auth/verify",
            queryParams = mapOf("token" to token)
        )
    }
    override suspend fun forgotPassword(email: String): EmptyResult<DataError.Remote> {
        return httpClient.post(
            route = "/auth/forgot-password",
           body = EmailRequest(email)
        )
    }

    override suspend fun resetPassword(
        newPassword: String,
        token: String
    ): EmptyResult<DataError.Remote> {
        return httpClient.post(
            route = "/auth/reset-password",
            body = ResetPasswordRequest(
                newPassword=newPassword,
                token=token
            )
        )
    }
}