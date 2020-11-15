package com.elliottco.pokequiz.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Question(@StringRes val textResId: Int, val answer: Boolean, @DrawableRes val imageResId: Int)