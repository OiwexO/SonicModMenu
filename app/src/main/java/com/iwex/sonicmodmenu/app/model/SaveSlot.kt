package com.iwex.sonicmodmenu.app.model

data class SaveSlot(
    val character: Int = 0,
    val lives: Int = 0,
    val score: Int = 0,
    val bonusScore: Int = 0,
    val stage: Int = 0,
    val emeralds: Int = 0,
) {
    constructor(saveSlotData: IntArray) : this(
        character = saveSlotData.getOrElse(0) { 0 },
        lives = saveSlotData.getOrElse(1) { 0 },
        score = saveSlotData.getOrElse(2) { 0 },
        bonusScore = saveSlotData.getOrElse(3) { 0 },
        stage = saveSlotData.getOrElse(4) { 0 },
        emeralds = saveSlotData.getOrElse(5) { 0 }
    )
}

fun SaveSlot.asIntArray(): IntArray {
    return intArrayOf(
        character,
        lives,
        score,
        bonusScore,
        stage,
        emeralds
    )
}