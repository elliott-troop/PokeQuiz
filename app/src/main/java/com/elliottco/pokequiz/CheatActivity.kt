package com.elliottco.pokequiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

// Set with package name to avoid name collisions from other apps
const val EXTRA_ANSWER_IS_TRUE = "com.elliottco.pokequiz.answer_is_true"
const val EXTRA_ANSWER_IS_SHOWN = "com.elliottco.pokequiz.answer_is_shown"

class CheatActivity : AppCompatActivity() {

    companion object {
        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            }
        }
    }

    private lateinit var showAnswerButton: Button
    private lateinit var answerTextView: TextView

    private var answerIsTrue = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        answerTextView = findViewById(R.id.answerTextView)
        showAnswerButton = findViewById(R.id.showAnswerButton)

        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)

        showAnswerButton.setOnClickListener { displayAnswer() }
    }

    private fun displayAnswer() {
        val answerText = when {
            answerIsTrue -> R.string.button_true_text
            else -> R.string.button_false_text
        }

        answerTextView.setText(answerText)
        setAnswerShownResult(true)
    }

    private fun setAnswerShownResult(isAnswerShown: Boolean) {
        val data = Intent().apply {
            putExtra(EXTRA_ANSWER_IS_SHOWN, isAnswerShown)
        }
        setResult(Activity.RESULT_OK, data)
    }
}