package domain

import androidx.compose.runtime.mutableStateOf

object GameSessionController {
    var currentLevel: Int = 1
    var isFiftyFiftyUsed: Boolean = false
    var isAudienceHelpUsed: Boolean = false
    var isStatisticsHelpUsed: Boolean = false
    var stillInGame = mutableStateOf(true)
        private set

    fun nextLevel() {
        if (currentLevel < 15) currentLevel++
    }

    fun useFiftyFifty() {
        isFiftyFiftyUsed = true
    }

    fun useAudienceHelp() {
        isAudienceHelpUsed = true
    }

    fun useStatisticsHelp() {
        isStatisticsHelpUsed = true
    }

    fun endGame() {
        stillInGame.value = false
    }

    fun resetGame() {
        currentLevel = 1
        isFiftyFiftyUsed = false
        isAudienceHelpUsed = false
        isStatisticsHelpUsed = false
        stillInGame.value = true
    }
}