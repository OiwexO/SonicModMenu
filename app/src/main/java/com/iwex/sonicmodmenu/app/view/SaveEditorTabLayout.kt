package com.iwex.sonicmodmenu.app.view

import android.annotation.SuppressLint
import android.content.Context
import android.widget.LinearLayout
import android.widget.SeekBar
import androidx.core.view.setPadding
import com.iwex.sonicmodmenu.app.util.MenuDesign
import com.iwex.sonicmodmenu.app.util.MenuWidgetFactory
import com.iwex.sonicmodmenu.app.viewmodel.MenuViewModel

@SuppressLint("ViewConstructor")
class SaveEditorTabLayout(
    context: Context, private val viewModel: MenuViewModel
) : LinearLayout(context) {

    private val saveSlotSeekbar: SeekBar

    companion object {
        private const val LABEL_SAVE_SLOT_SEEKBAR = "save slot: %d"

        private const val MAX_SAVE_SLOT = 3
    }

    init {
        orientation = VERTICAL
        setBackgroundColor(MenuDesign.Colors.TAB_BACKGROUND)
        setPadding(MenuDesign.Measurements.TAB_PADDING)

        saveSlotSeekbar = MenuWidgetFactory.addSeekBar(
            LABEL_SAVE_SLOT_SEEKBAR,
            MAX_SAVE_SLOT,
            0,
            viewModel::onSaveSlotChange,
            context,
            this
        )


    }

}