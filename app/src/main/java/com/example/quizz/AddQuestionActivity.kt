package com.example.quizz

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_add_question.*
import android.content.Intent
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import org.jetbrains.anko.db.*

class AddQuestionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_question)
        var id: Long = intent.getLongExtra("id", 0)
        if (id.toInt() != 0) {
            showQuestionEditable(id)
        }
    }

    fun showQuestionEditable(id: Long) {
        database.use {
            val res = select("Question").whereArgs("id=$id")
            res.exec {
                var q = this.parseSingle(classParser<QuestionsFragment.Question>())
                questionEdit.setText(q.question)
                select("Reponse").whereArgs("idQuestion=$id").exec {
                    var result = this.parseList(classParser<QuestionsFragment.Reponse>())
                    txt1.setText(result[0].reponse)
                    chk1.isChecked = result[0].isGoodAnswer
                    txt2.setText(result[1].reponse)
                    chk1.isChecked = result[0].isGoodAnswer
                    txt3.setText(result[2].reponse)
                    chk1.isChecked = result[0].isGoodAnswer
                    button_add.setOnClickListener {
                        database.use {
                            update("Reponse", "reponse" to txt1.text.toString(), "isGoodAnswer" to chk1.isChecked)
                                .whereArgs("id={idqi} and idQuestion = {idq}", "idqi" to result[0].id, "idq" to id)
                                .exec()
                            update("Reponse", "reponse" to txt1.text.toString(), "isGoodAnswer" to chk1.isChecked)
                                .whereArgs("id={idqi} and idQuestion = {idq}", "idqi" to result[1].id, "idq" to id)
                                .exec()
                            update("Reponse", "reponse" to txt2.text.toString(), "isGoodAnswer" to chk2.isChecked)
                                .whereArgs("id={idqi} and idQuestion = {idq}", "idqi" to result[2].id, "idq" to id)
                                .exec()
                            update("Question", "question" to questionEdit.text.toString())
                                .where("id = {idq}", "idq" to id)
                                .exec()
                            finish()
                            onBackPressed()
                        }
                    }
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finishAndPassData()
        this.onBackPressed()
        return true
    }

    override fun onBackPressed() {
        finishAndPassData()
    }

    fun finishAndPassData() {
        var intent = Intent();
        intent.putExtra("questions", true)
        setResult(RESULT_OK, intent);
        finish()
    }

    fun button_add_Onclick(view: View) {
        var id: Long = intent.getLongExtra("id", 0)
        if (id.toInt() != 0) {
            return
        }
        try {
            database.use {
                val q = ContentValues()
                q.put("question", questionEdit.text.toString())

                var idQuestion: Long = insert("Question", null, q)

                val r1 = ContentValues()
                r1.put("reponse", txt1.text.toString())
                r1.put("isGoodAnswer", chk1.isChecked)
                r1.put("idQuestion", idQuestion.toString())

                val r2 = ContentValues()
                r2.put("reponse", txt2.text.toString())
                r2.put("isGoodAnswer", chk2.isChecked)
                r2.put("idQuestion", idQuestion.toString())

                val r3 = ContentValues()
                r3.put("reponse", txt3.text.toString())
                r3.put("isGoodAnswer", chk3.isChecked)
                r3.put("idQuestion", idQuestion.toString())

                insert("Reponse", null, r1)
                insert("Reponse", null, r3)
                insert("Reponse", null, r2)
                finish()
                onBackPressed()
            }
        } catch (e: Exception) {
            Toast.makeText(applicationContext, "Bad input", Toast.LENGTH_LONG).show()
        }

    }
}

