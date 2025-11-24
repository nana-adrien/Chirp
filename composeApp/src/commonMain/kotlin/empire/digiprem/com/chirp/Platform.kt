package empire.digiprem.com.chirp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform