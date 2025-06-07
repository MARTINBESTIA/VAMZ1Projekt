package data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface QuestionDao {
    @Query("SELECT * FROM questions WHERE difficulty = :difficulty AND alreadyShowed = false AND sector = :sector ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomUnshownQuestion(difficulty: Int, sector: Int): Questions?

    @Update()
    suspend fun updateQuestion(question: Questions)

    @Query("Update questions SET alreadyShowed = 'N' WHERE difficulty = :difficulty" )
    suspend fun resetQuestionsUnread(difficulty: Int)

    @Insert
    suspend fun insertAllQuestions(questions: List<Questions>)

    @Query("SELECT COUNT(*) FROM questions")
    suspend fun getQuestionCount(): Int

}