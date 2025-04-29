package data

class QuestionProcessor(private val questionDao: QuestionDao, private val difficulty: Int, private val sector: Int) {

    private var currentQuestion: Questions? = null

    suspend fun loadQuestion(): Questions? {
        currentQuestion = questionDao.getRandomUnshownQuestion(difficulty, sector)
        if (currentQuestion == null) {
            questionDao.resetQuestionsUnread(difficulty)
            currentQuestion = questionDao.getRandomUnshownQuestion(difficulty, sector)
        }
        questionDao.updateQuestion(currentQuestion)
        return currentQuestion
    }
    fun getCorrectAnswer(): Char? {
        return currentQuestion?.correctAnswer
    }
    fun getSector(): Int? {
        return currentQuestion?.sector
    }
    fun getQuestion(): String? {
        return currentQuestion?.question
    }
    fun getAnswerA(): String? {
        return currentQuestion?.answerA
    }
    fun getAnswerB(): String? {
        return currentQuestion?.answerB
    }
    fun getAnswerC(): String? {
        return currentQuestion?.answerC
    }
    fun getAnswerD(): String? {
        return currentQuestion?.answerD
    }
}