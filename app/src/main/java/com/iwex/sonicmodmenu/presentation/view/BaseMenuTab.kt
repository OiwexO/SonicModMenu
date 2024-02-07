package com.iwex.sonicmodmenu.presentation.view

import android.content.Context
import android.widget.LinearLayout
import androidx.core.view.setPadding
import com.iwex.sonicmodmenu.util.MenuDesign

open class BaseMenuTab(context: Context) : LinearLayout(context) {

    init {
        orientation = VERTICAL
        initializeLayout()
    }

    private fun initializeLayout() {
        setBackgroundColor(MenuDesign.Colors.TAB_BACKGROUND)
        setPadding(MenuDesign.Measurements.TAB_PADDING)
    }

}