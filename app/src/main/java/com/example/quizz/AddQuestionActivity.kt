package com.example.quizz

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_add_question.*
import android.content.Intent
import android.util.Log
import android.view.MenuItem
import android.view.View
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.parseList
import org.jetbrains.anko.db.select

class AddQuestionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_question)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        val data = Intent()
        data.putExtra("questions", true)
        setResult(RESULT_OK, data);
        super.onBackPressed()
    }

    fun button_add_Onclick(view: View) {
        database.use {
            val q = ContentValues()
            q.put("question", questionEdit.text.toString())
            var idQuestion: Long = insert("Question", null, q)

            val r1 = ContentValues()
            r1.put("reponse", txt1.text.toString())
            r1.put("isGoodAnswer", chk1.isChecked)
            r1.put("idQuestion", idQuestion.toString())
            insert("Reponse", null, r1)

            val r2 = ContentValues()
            r2.put("reponse", txt2.text.toString())
            r2.put("isGoodAnswer", chk2.isChecked)
            r2.put("idQuestion", idQuestion.toString())
            insert("Reponse", null, r2)

            val r3 = ContentValues()
            r3.put("reponse", txt3.text.toString())
            r3.put("isGoodAnswer", chk3.isChecked)
            r3.put("idQuestion", idQuestion.toString())
            insert("Reponse", null, r3)

            val res = select("reponse")
            res.exec {
                val rowParser = classParser<QuestionsFragment.Reponse>()
                val listQuestion = parseList(rowParser)
                listQuestion.forEach {
                    Log.d("sqlite",it.reponse)
                    Log.d("sqlite",it.isGoodAnswer.toString())
                }
            }
        }
    }
}

