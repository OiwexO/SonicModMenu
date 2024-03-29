package com.iwex.sonicmodmenu.presentation.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.PixelFormat
import android.os.Build
import android.util.Base64
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.WindowManager
import android.view.WindowManager.LayoutParams.*
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.LinearLayout.LayoutParams
import android.widget.RelativeLayout
import android.widget.RelativeLayout.LayoutParams.MATCH_PARENT
import android.widget.ScrollView
import androidx.core.view.isVisible
import androidx.core.view.setMargins
import com.iwex.sonicmodmenu.presentation.MenuWidgetFactory
import com.iwex.sonicmodmenu.presentation.resource.Colors
import com.iwex.sonicmodmenu.presentation.resource.Dimensions
import com.iwex.sonicmodmenu.presentation.resource.FloatingIcon
import com.iwex.sonicmodmenu.presentation.view.menuTabs.BaseMenuTab

@SuppressLint("ViewConstructor")
class FloatingMenu(
    context: Context,
    private val gameTab: BaseMenuTab,
    private val saveEditorTab: BaseMenuTab,
    private val otherTab: BaseMenuTab
) : RelativeLayout(context) {
    val layoutParams = initLayoutParams(context)
    private val floatingIconView = initFloatingIconView(context)
    private val floatingMenuLayout = initFloatingMenuLayout(context)
    private val tabHolderScrollView = initTabHolderScrollView(context)
    private val tabButtonsLayout = LinearLayout(context)
    private val closeMenuButton = initCloseMenuButton(context)
    private val defaultTab = gameTab

    companion object {
        private const val TAG = "FloatingMenu.kt"
        private const val LABEL_MENU_TITLE = "Sonic mod by OiwexO"
        private const val LABEL_GAME_BUTTON = "GAME"
        private const val LABEL_SAVES_BUTTON = "SAVES"
        private const val LABEL_OTHER_BUTTON = "OTHER"
        private const val LABEL_CLOSE_MENU_BUTTON = "CLOSE MENU"
    }

    init {
        floatingMenuLayout.addView(tabHolderScrollView)
        setActiveTab(defaultTab)
        floatingMenuLayout.addView(tabButtonsLayout)
        addTabButton(LABEL_GAME_BUTTON, gameTab, context)
        addTabButton(LABEL_SAVES_BUTTON, saveEditorTab, context)
        addTabButton(LABEL_OTHER_BUTTON, otherTab, context)
        floatingMenuLayout.addView(closeMenuButton)
        closeMenuButton.setOnClickListener {
            setActiveTab(defaultTab)
            toggleIconAndMenuVisibility()
        }
        addView(floatingIconView)
        addView(floatingMenuLayout)
    }

    private fun toggleIconAndMenuVisibility() {
        if (floatingIconView.isVisible) {
            floatingIconView.visibility = GONE
            floatingMenuLayout.visibility = VISIBLE
        } else {
            floatingIconView.visibility = VISIBLE
            floatingMenuLayout.visibility = GONE
        }
    }

    private fun initLayoutParams(context: Context): WindowManager.LayoutParams {
        val windowType =
            if (Build.VERSION.SDK_INT >= 26) TYPE_APPLICATION_OVERLAY else TYPE_APPLICATION
        return WindowManager.LayoutParams(
            WRAP_CONTENT,
            WRAP_CONTENT,
            Dimensions.getInstance(context).iconPositionX,
            Dimensions.getInstance(context).iconPositionY,
            windowType,
            FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )
    }

    private fun initFloatingIconView(context: Context): ImageView {
        val decoded: ByteArray = Base64.decode(FloatingIcon.BASE64_ENCODED_ICON, Base64.DEFAULT)
        return ImageView(context).apply {
            layoutParams = LayoutParams(
                Dimensions.getInstance(context).iconSizePx,
                Dimensions.getInstance(context).iconSizePx
            )
            scaleType = ImageView.ScaleType.FIT_XY
            setImageBitmap(BitmapFactory.decodeByteArray(decoded, 0, decoded.size))
        }
    }

    private fun initFloatingMenuLayout(context: Context): LinearLayout {
        return LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = LayoutParams(
                Dimensions.getInstance(context).menuWidthPx,
                Dimensions.getInstance(context).menuHeightPx
            )
            setBackgroundColor(Colors.MENU_BACKGROUND)
            MenuWidgetFactory.addTitle(LABEL_MENU_TITLE, context, this)
            visibility = GONE
        }
    }

    private fun initTabHolderScrollView(context: Context): ScrollView {
        return ScrollView(context).apply {
            layoutParams = LayoutParams(MATCH_PARENT, 0, 1f).apply {
                setMargins(Dimensions.getInstance(context).buttonMarginPx)
            }
        }
    }

    private fun initCloseMenuButton(context: Context): Button {
        return MenuWidgetFactory.addButton(
            LABEL_CLOSE_MENU_BUTTON,
            true,
            context,
            null
        )
    }

    private fun setActiveTab(tab: BaseMenuTab) {
        tabHolderScrollView.removeAllViews()
        tabHolderScrollView.addView(tab)
    }

    private fun addTabButton(label: String, tab: BaseMenuTab, context: Context) {
        MenuWidgetFactory.addButton(label, false, context, tabButtonsLayout).apply {
            layoutParams = LayoutParams(0, WRAP_CONTENT, 1f).apply {
                setMargins(
                    Dimensions.getInstance(context).buttonMarginPx,
                    0,
                    Dimensions.getInstance(context).buttonMarginPx,
                    0
                )
            }
            setOnClickListener { setActiveTab(tab) }
        }
    }

    override fun performClick(): Boolean {
        super.performClick()
        toggleIconAndMenuVisibility()
        return true
    }

}
