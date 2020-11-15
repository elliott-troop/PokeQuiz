package com.elliottco.pokequiz.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class PokemonQuestion(@StringRes val textResId: Int, val answer: Boolean, val pokedexNumber: Int? = null)