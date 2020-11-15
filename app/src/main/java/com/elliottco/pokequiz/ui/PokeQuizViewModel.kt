package com.elliottco.pokequiz.ui

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.elliottco.pokequiz.R
import com.elliottco.pokequiz.model.PokemonQuestion

class PokeQuizViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var currentIndex = 0
    var isCheater = false

    private val questionBank = listOf(
        PokemonQuestion(R.string.pokemon_question_one, false, 130),
        PokemonQuestion(R.string.pokemon_question_two, false, 111),
        PokemonQuestion(R.string.pokemon_question_three, false, 125),
        PokemonQuestion(R.string.pokemon_question_four, true, 92),
        PokemonQuestion(R.string.pokemon_question_five, false, 113),
        PokemonQuestion(R.string.pokemon_question_six, true, 386),
        PokemonQuestion(R.string.pokemon_question_seven, true, 108),
        PokemonQuestion(R.string.pokemon_question_eight, false, 2)
    )

    val currentPokemonQuestionResId: Int
        get() = questionBank[currentIndex].textResId

    val currentPokemonQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer

    val currentPokemonQuestionPokedexNumber: Int?
        get() = questionBank[currentIndex].pokedexNumber

    // Records the index of the cheated questions
    val hasCheatedMap = HashMap<Int, Boolean>()


    fun moveToPrevious() {
        if(currentIndex != 0)
            currentIndex = (currentIndex - 1) % questionBank.size

        isCheater = false
    }

    fun moveToNext() {
        if(currentIndex != questionBank.size - 1) {
            currentIndex = (currentIndex + 1) % questionBank.size
        }

        isCheater = false
    }

    fun hidePreviousButton() = currentIndex == 0
    fun hideNextButton() = currentIndex == questionBank.size - 1

}