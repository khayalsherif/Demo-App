package com.vholodynskyi.assignment.common

import android.view.View

interface SwipeListener {
    fun onSwipe(view: View, position: Int)
}