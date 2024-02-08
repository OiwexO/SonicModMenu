package com.iwex.sonicmodmenu.presentation.viewmodel

import com.iwex.sonicmodmenu.domain.NativeBridge
import com.iwex.sonicmodmenu.domain.model.SaveSlot
import com.iwex.sonicmodmenu.domain.model.toIntArray

class SaveEditorTabViewModel {
    private var saveSlotIndex = 0
    private var saveSlot = SaveSlot()

    fun onSaveSlotChange(slotIndex: Int): SaveSlot {
        saveSlotIndex = slotIndex
        val saveSlotArray = NativeBridge.readSaveFile(saveSlotIndex)
        saveSlot = SaveSlot(saveSlotArray)
        return saveSlot
    }

    fun onCharacterChange(character: Int) {
        saveSlot = saveSlot.copy(character = character)
    }

    fun onLivesChange(lives: Int) {
        saveSlot = saveSlot.copy(lives = lives)
    }

    fun onScoreChange(score: Int) {
        saveSlot = saveSlot.copy(score = score)
    }

    fun onBonusScoreChange(bonusScore: Int) {
        saveSlot = saveSlot.copy(bonusScore = bonusScore)
    }

    fun onStageChange(stage: Int) {
        saveSlot = saveSlot.copy(stage = stage)
    }

    fun onEmeraldsChange(emeralds: Int) {
        saveSlot = saveSlot.copy(emeralds = emeralds)
    }

    fun editSlot(): Boolean {
        val saveSlotData = saveSlot.toIntArray()
        return NativeBridge.writeSaveFile(saveSlotIndex, saveSlotData)
    }

}