package empire.digiprem.com.chat.database

import androidx.room.RoomDatabase

expect class DatabaseFactory {
    fun create():RoomDatabase.Builder<ChirpChatDatabase>
}