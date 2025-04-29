package data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update

@Dao
interface QuestionDao {
    @Query("SELECT * FROM questions WHERE difficulty = :difficulty AND alreadyShowed = 'N' AND sector = :sector ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomUnshownQuestion(difficulty: Int, sector: Int): Questions?

    @Update()
    suspend fun updateQuestion(question: Questions?)

    @Query("Update questions SET alreadyShowed = 'N' WHERE difficulty = :difficulty" )
    suspend fun resetQuestionsUnread(difficulty: Int)

}