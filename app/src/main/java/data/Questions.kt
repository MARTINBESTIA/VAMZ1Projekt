package data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "questions")
data class Questions(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val question: String,
    val answerA: String,
    val answerB: String,
    val answerC: String,
    val answerD: String,
    val difficulty: Int,
    val correctAnswer: Char,
    val sector: Int,
    val alreadyShowed: Boolean
)

