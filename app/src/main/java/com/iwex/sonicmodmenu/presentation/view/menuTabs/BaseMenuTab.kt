package com.iwex.sonicmodmenu.presentation.view.menuTabs

import android.content.Context
import android.widget.LinearLayout
import androidx.core.view.setPadding
import com.iwex.sonicmodmenu.presentation.resource.Colors
import com.iwex.sonicmodmenu.presentation.resource.Dimensions

open class BaseMenuTab(context: Context) : LinearLayout(context) {

    init {
        orientation = VERTICAL
        initializeLayout(context)
    }

    private fun initializeLayout(context: Context) {
        setBackgroundColor(Colors.TAB_BACKGROUND)
        setPadding(Dimensions.getInstance(context).tabPaddingPx)
    }

}