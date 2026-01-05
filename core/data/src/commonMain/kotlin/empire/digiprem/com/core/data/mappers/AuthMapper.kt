package empire.digiprem.com.core.data.mappers

import empire.digiprem.com.core.data.dto.AuthInfoSerializable
import empire.digiprem.com.core.data.dto.UserSerializable
import empire.digiprem.com.core.domain.auth.AuthInfo
import empire.digiprem.com.core.domain.auth.User

fun AuthInfoSerializable.toDomain(): AuthInfo{
    return AuthInfo(
        accessToken=accessToken,
        refreshToken=refreshToken,
        user=user.toDomain()
    )
}
fun UserSerializable.toDomain(): User {
    return User(
        id=id,
        email=email,
        username=username,
        profilePicture=profilePicture,
        hasVerifiedEmail = hasVerifiedEmail
    )
}