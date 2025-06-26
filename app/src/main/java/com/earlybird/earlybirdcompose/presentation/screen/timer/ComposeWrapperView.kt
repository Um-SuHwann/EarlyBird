package com.earlybird.earlybirdcompose.presentation.screen.timer

import android.content.Context
import android.widget.FrameLayout
import androidx.compose.ui.platform.ComposeView

class ComposeWrapperView(context: Context) : FrameLayout(context) {

    private val composeView: ComposeView = ComposeView(context).apply {
        setContent {
            TimerScreen()
        }
    }

    init {
        addView(composeView,
            LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        )
    }
}