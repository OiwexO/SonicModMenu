package com.iwex.sonicmodmenu.app.viewmodel

import com.iwex.sonicmodmenu.app.NativeBridge
import com.iwex.sonicmodmenu.app.model.SaveSlot
import com.iwex.sonicmodmenu.app.model.asIntArray

class SaveEditorTabViewModel {
    private var saveSlotIndex = 0
    private var saveSlot = SaveSlot()

    fun onSaveSlotChange(slotIndex: Int): SaveSlot {
        saveSlotIndex = slotIndex
        val saveSlotData = NativeBridge.readSaveFile(slotIndex)
        saveSlot = SaveSlot(saveSlotData)
        return saveSlot.copy()
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
        val saveSlotData = saveSlot.asIntArray()
        return NativeBridge.writeSaveFile(saveSlotIndex, saveSlotData)
    }

}