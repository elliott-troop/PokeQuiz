package com.elliottco.pokequiz

import android.widget.TextView
import androidx.lifecycle.ViewModel
import com.elliottco.pokequiz.model.Question

class PokeQuizViewModel: ViewModel() {

    var currentIndex = 0
    var isCheater = false

    private val questionBank = listOf(
        Question(R.string.pokemon_question_one, false, R.drawable.ic_gyarados),
        Question(R.string.pokemon_question_two, false, R.drawable.ic_rhyhorn),
        Question(R.string.pokemon_question_three, true, R.drawable.ic_dark_type_icon),
        Question(R.string.pokemon_question_four, true, R.drawable.ic_gastly),
        Question(R.string.pokemon_question_five, false, R.drawable.ic_chansey),
        Question(R.string.pokemon_question_six, true, R.drawable.ic_deoxys)
    )

    val currentQuestionResId: Int
        get() = questionBank[currentIndex].textResId

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer

    val currentQuestionImage: Int
        get() = questionBank[currentIndex].imageResId

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