package com.iwex.sonicmodmenu.presentation.view.menuTabs

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Button
import android.widget.SeekBar
import android.widget.Toast
import com.iwex.sonicmodmenu.presentation.viewmodel.SaveEditorTabViewModel
import com.iwex.sonicmodmenu.util.GameVersionChecker
import com.iwex.sonicmodmenu.presentation.MenuWidgetFactory

@SuppressLint("ViewConstructor")
class SaveEditorTab(
    context: Context, private val viewModel: SaveEditorTabViewModel
) : BaseMenuTab(context) {

    private val saveSlotSeekbar: SeekBar
    private val characterSeekbar: SeekBar
    private val livesInput: Button
    private val scoreInput: Button
    private val bonusScoreInput: Button
    private val stageInput: Button
    private val emeraldsInput: Button
    private val editSlotButton: Button

    companion object {
        private const val LABEL_SAVE_SLOT = "save slot"
        private const val LABEL_CHARACTER = "character"
        private const val LABEL_LIVES = "lives"
        private const val LABEL_SCORE = "score"
        private const val LABEL_BONUS_SCORE = "bonus score"
        private const val LABEL_STAGE = "stage"
        private const val LABEL_EMERALDS = "emeralds"
        private const val LABEL_EDIT_SLOT = "EDIT SLOT"
        private const val TOAST_ON_SUCCESS = "Save file has been edited successfully"
        private const val TOAST_ON_FAILURE = "An error occurred while editing save file"

        private const val MAX_SAVE_SLOT = 3
        private const val MAX_CHARACTER = 3
        private const val MAX_LIVES = 99
        private const val MAX_SCORE = 999_999
        private const val MAX_BONUS_SCORE = 999_999
        private const val MAX_STAGE_SONIC1 = 19
        private const val MAX_STAGE_SONIC2 = 22
        private const val MAX_EMERALDS_SONIC1 = 111111
        private const val MAX_EMERALDS_SONIC2 = 1111111
        private val CHARACTERS = arrayOf("Sonic", "Tails", "Knuckles", "Sonic & Tails")

    }

    init {
        saveSlotSeekbar = MenuWidgetFactory.addSeekBar(
            LABEL_SAVE_SLOT,
            MAX_SAVE_SLOT,
            0,
            ::readSaveSlot,
            context,
            this
        )
        characterSeekbar = MenuWidgetFactory.addSeekBar(
            LABEL_CHARACTER,
            MAX_CHARACTER,
            0,
            viewModel::onCharacterChange,
            context,
            this,
            CHARACTERS
        )
        livesInput = MenuWidgetFactory.addNumberInput(
            LABEL_LIVES,
            MAX_LIVES,
            viewModel::onLivesChange,
            context,
            this
        )
        scoreInput = MenuWidgetFactory.addNumberInput(
            LABEL_SCORE,
            MAX_SCORE,
            viewModel::onScoreChange,
            context,
            this
        )
        bonusScoreInput = MenuWidgetFactory.addNumberInput(
            LABEL_BONUS_SCORE,
            MAX_BONUS_SCORE,
            viewModel::onBonusScoreChange,
            context,
            this
        )
        stageInput = MenuWidgetFactory.addNumberInput(
            LABEL_STAGE,
            if (GameVersionChecker.isSonic2) MAX_STAGE_SONIC2 else MAX_STAGE_SONIC1,
            viewModel::onStageChange,
            context,
            this
        )
        emeraldsInput = MenuWidgetFactory.addNumberInput(
            LABEL_EMERALDS,
            if (GameVersionChecker.isSonic2) MAX_EMERALDS_SONIC2 else MAX_EMERALDS_SONIC1,
            viewModel::onEmeraldsChange,
            context,
            this,
            true
        )
        editSlotButton = MenuWidgetFactory.addButton(
            LABEL_EDIT_SLOT,
            true,
            context,
            this
        )
        editSlotButton.setOnClickListener {
            val result = viewModel.editSlot()
            Toast.makeText(
                context,
                if (result) TOAST_ON_SUCCESS else TOAST_ON_FAILURE,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun readSaveSlot(slotIndex: Int) {
        val saveSlotState = viewModel.onSaveSlotChange(slotIndex)
        characterSeekbar.progress = saveSlotState.character
        livesInput.text = String.format("%s: %d", LABEL_LIVES, saveSlotState.lives)
        scoreInput.text = String.format("%s: %d", LABEL_SCORE, saveSlotState.score)
        bonusScoreInput.text = String.format("%s: %d", LABEL_BONUS_SCORE, saveSlotState.bonusScore)
        stageInput.text = String.format("%s: %d", LABEL_STAGE, saveSlotState.stage)
        emeraldsInput.text = String.format(
            "%s: %s",
            LABEL_EMERALDS,
            Integer.toBinaryString(saveSlotState.emeralds)
        )
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        readSaveSlot(saveSlotSeekbar.progress)
    }

}