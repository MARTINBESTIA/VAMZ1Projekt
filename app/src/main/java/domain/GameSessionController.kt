package domain

import androidx.compose.runtime.mutableStateOf

object GameSessionController {
    var currentLevel: Int = 1
    var isFiftyFiftyUsed = mutableStateOf(false)
    var isAudienceHelpUsed = mutableStateOf(false)
    var isStatisticsHelpUsed = mutableStateOf(false)
    var stillInGame = mutableStateOf(true)
        private set

    fun nextLevel() {
        if (currentLevel < 15) currentLevel++
    }

    fun useFiftyFifty() {
        isFiftyFiftyUsed.value = true
    }

    fun useAudienceHelp() {
        isAudienceHelpUsed.value = true
    }

    fun useStatisticsHelp() {
        isStatisticsHelpUsed.value = true
    }

    fun endGame() {
        stillInGame.value = false
    }

    fun resetGame() {
        currentLevel = 1
        isFiftyFiftyUsed.value = false
        isAudienceHelpUsed.value = false
        isStatisticsHelpUsed.value = false
        stillInGame.value = true
    }
}