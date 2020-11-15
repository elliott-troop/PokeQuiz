package com.elliottco.pokequiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.elliottco.pokequiz.model.Question

class MainActivity : AppCompatActivity() {

    companion object {
        private const val KEY_INDEX = "index"
        private const val REQUEST_CODE_CHEAT = 0
    }

    private lateinit var questionText: TextView
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var cheatButton: Button
    private lateinit var previousButton: ImageButton
    private lateinit var nextButton: ImageButton
    private lateinit var pokemonImage: ImageView

    private val pokeQuizViewModel: PokeQuizViewModel by lazy {
        ViewModelProvider(this).get(PokeQuizViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        pokeQuizViewModel.currentIndex = currentIndex

        questionText = findViewById(R.id.questionText)
        trueButton = findViewById(R.id.trueButton)
        falseButton = findViewById(R.id.falseButton)
        cheatButton = findViewById(R.id.cheatButton)
        previousButton = findViewById(R.id.previousButton)
        nextButton = findViewById(R.id.nextButton)
        pokemonImage = findViewById(R.id.pokemonImage)


        trueButton.setOnClickListener { checkAnswer(true) }
        falseButton.setOnClickListener { checkAnswer(false) }
        cheatButton.setOnClickListener {
            startCheatActivity()
            updateUi()
        }

        previousButton.setOnClickListener {
            pokeQuizViewModel.moveToPrevious()
            updateUi()
        }

        nextButton.setOnClickListener {
            pokeQuizViewModel.moveToNext()
            updateUi()
        }

        updateUi()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        // Save the current index on process death
        outState.putInt(KEY_INDEX, pokeQuizViewModel.currentIndex)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK) {
            return
        }

        if(requestCode == REQUEST_CODE_CHEAT) {
            pokeQuizViewModel.isCheater = data?.getBooleanExtra(EXTRA_ANSWER_IS_SHOWN, false) ?: false
            pokeQuizViewModel.hasCheatedMap[pokeQuizViewModel.currentIndex] = data?.getBooleanExtra(EXTRA_ANSWER_IS_SHOWN, false) ?: false
        }
    }

    /**
     * Compares the user's answer with the question's answer and displays a toast of the results
     *
     * If the user cheated, JUDGE THEM!
     */
    private fun checkAnswer(answer: Boolean) {
        // A boolean indicating whether the user has cheated for this question
        val hasCheated = pokeQuizViewModel.hasCheatedMap[pokeQuizViewModel.currentIndex]

        val messageResId = when {
            pokeQuizViewModel.isCheater -> R.string.toast_judgemental
            (hasCheated != null && hasCheated) -> R.string.toast_always_cheater
            answer == pokeQuizViewModel.currentQuestionAnswer -> R.string.toast_correct_text
            else -> R.string.toast_incorrect_text
        }

        Toast.makeText(applicationContext, messageResId, Toast.LENGTH_SHORT).show()
    }

    /**
     * Updates the questionTextView and pokemonImageView, as well as show/hide the previous/next buttons
     */
    private fun updateUi() {

        questionText.setText(pokeQuizViewModel.currentQuestionResId)
        pokemonImage.setImageResource(pokeQuizViewModel.currentQuestionImage)

        if(pokeQuizViewModel.hidePreviousButton())
            previousButton.visibility = View.GONE
        else
            previousButton.visibility = View.VISIBLE

        if(pokeQuizViewModel.hideNextButton())
            nextButton.visibility = View.GONE
        else
            nextButton.visibility = View.VISIBLE
    }

    /**
     * Start the Cheat Activity
     */
    private fun startCheatActivity() {
        val answerIsTrue = pokeQuizViewModel.currentQuestionAnswer
        val intent = CheatActivity.newIntent(this, answerIsTrue)
        startActivityForResult(intent, REQUEST_CODE_CHEAT)
    }
}