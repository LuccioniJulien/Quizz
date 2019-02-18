package com.example.quizz

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.core.view.forEach
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_quiz.*

class QuizActivity : AppCompatActivity() {

    val quizzHelp = HelperQuizz().iterateThat().iterator()
    var reponses: MutableList<Pair<String, Boolean>>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        setQuestion()
        var name = intent.getStringExtra("Nickname")
        titleQuestion.text = "$name, here the question : ";
    }

    fun answer_button_OnClick(view: View) {
        val id = reponseGroup.checkedRadioButtonId
        if (id == -1) {
            showSnackBar("Il faut choisir une réponse", view)
            return
        }
        val rb = findViewById<RadioButton>(id)
        val key: String = rb.text.toString()
        val message = if (reponses!!.find { it.first == key }!!.second) {
            setScore()
            setColor()
            "Good answer!"
        } else {
            setColor(false)
            "Bad answer!"
        }
        button_answer.isEnabled = false
        button_next.isVisible = true
        showSnackBar(message, view)
    }

    private fun clearUI() {
        reponseGroup.forEach {
            (it as RadioButton).apply {
                setTextColor(Color.BLACK)
            }
        }
    }

    private fun setColor(isWin: Boolean = true) {
        reponseGroup.forEach {
            val rbt = (it as RadioButton)
            val res = reponses!!.find { x -> x.first == rbt.text }
            if (res!!.second) {
                rbt.setTextColor(Color.GREEN)
            } else if (!isWin) {
                rbt.setTextColor(Color.RED)
            }
        }
    }

    fun next_button_OnClick(view: View) {
        next()
        clearUI()
        reponseGroup.clearCheck()
        button_answer.isEnabled = true
        button_next.isVisible = false
    }

    private fun next() {
        if (quizzHelp.hasNext()) {
            setQuestion()
        } else {
            setHightScoreAndShowModal()
        }
    }

    private fun setScore() {
        var currentScore = txtScore.text.toString().toInt()
        currentScore += 1
        txtScore.text = currentScore.toString()
    }

    private fun setHightScoreAndShowModal() {
        val name = intent.getStringExtra("Nickname")
        val score = txtScore.text.toString()
        getSharedPreferences("myPrefs", Context.MODE_PRIVATE).apply {
            this.edit().putInt(name, score.toInt()).apply()
        }
        AlertDialog.Builder(this).create().apply {
            setTitle("The end")
            setMessage("The game has ended. \nYour final score is: $score/")
            setButton(AlertDialog.BUTTON_NEUTRAL, "OK") { dialog, _ -> dialog.dismiss() }
            show()
        }

    }

    private fun showSnackBar(message: String, view: View) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
            .setAction(message, null).show()
    }

    private fun setQuestion() {
        val row = quizzHelp.next()
        question.text = row.key
        reponses = row.value
        reponseUno.text = row.value[0].first
        reponseDeusio.text = row.value[1].first
        reponseTercio.text = row.value[2].first
    }

}
