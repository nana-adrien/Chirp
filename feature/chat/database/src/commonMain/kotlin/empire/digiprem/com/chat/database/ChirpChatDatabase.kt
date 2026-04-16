package empire.digiprem.com.chat.database

import androidx.room.Database
import androidx.room.RoomDatabase
import empire.digiprem.com.chat.database.dao.ChatDao
import empire.digiprem.com.chat.database.dao.ChatMessageDao
import empire.digiprem.com.chat.database.dao.ChatParticipantDao
import empire.digiprem.com.chat.database.dao.ChatParticipantsCrossRefDao
import empire.digiprem.com.chat.database.entites.ChatEntity
import empire.digiprem.com.chat.database.entites.ChatMessageEntity
import empire.digiprem.com.chat.database.entites.ChatParticipantCrossRef
import empire.digiprem.com.chat.database.entites.ChatParticipantEntity
import empire.digiprem.com.chat.database.view.LastMessageView

@Database(
    entities = [
        ChatEntity::class,
        ChatParticipantEntity::class,
        ChatMessageEntity::class,
        ChatParticipantCrossRef::class,
    ],
    views=[
        LastMessageView::class
    ],
    version = 1
)
abstract class ChirpChatDatabase:RoomDatabase(){
    abstract val chatDao:ChatDao
    abstract val chatParticipantDao:ChatParticipantDao
    abstract val chatMessageDao:ChatMessageDao
    abstract val chatParticipantsCrossRefDao:ChatParticipantsCrossRefDao

    companion object{
        const val DB_NAME="chirp.dp"

    }
}