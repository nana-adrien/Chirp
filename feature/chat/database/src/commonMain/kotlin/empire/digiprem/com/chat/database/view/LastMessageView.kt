package empire.digiprem.com.chat.database.view

import androidx.room.DatabaseView

@DatabaseView
data class LastMessageView(
    val messageId:String,
    val chatId:String,
    val senderId:String,
    val content:String,
    val timestamp:Long
)