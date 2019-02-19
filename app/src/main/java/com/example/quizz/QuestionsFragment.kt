package com.example.quizz


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_questions.view.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.parseList
import org.jetbrains.anko.db.select
import java.lang.Exception

class QuestionsFragment : Fragment() {

    var v: View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_questions, container, false)
        view.button_add.setOnClickListener {
            Intent(context, AddQuestionActivity::class.java).apply {
                startActivity(this)
            }
        }
        setRecycle(view)
        v = view
        return view
    }

    class Question(val id: Long, val question: String)
    class Reponse(val id: Long, val reponse: String, val isGoodAnswer: Boolean, idQuestion: Long)

    fun setRecycle(view: View) {
        var listeScores: ArrayList<Pair<Long, String>> = getQuestions()
        val adapter = QuestionsAdaptater(listeScores, { id: Long ->
            delete(id)
            setRecycle(view)
        }, { id: Long ->
            edit(id)
            setRecycle(view)
        })
        view.rcvQuestions.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        view.rcvQuestions.adapter = adapter
    }

    fun delete(id: Long) {
        try {
            context!!.database.use {
                delete("Question", "id = {userID}", "userID" to id)
            }
        } catch (e: Exception) {

        }
    }

    fun edit(id: Long) {
        Intent(context, AddQuestionActivity::class.java).apply {
            putExtra("id", id)
            startActivity(this)
        }
    }

    override fun onResume() {
        super.onResume()
        setRecycle(v!!)
    }

    private fun getQuestions(): ArrayList<Pair<Long, String>> {
        var listeScores: ArrayList<Pair<Long, String>> = arrayListOf()
        context!!.database.use {
            try {
                val res = select("Question")
                res.exec {
                    val rowParser = classParser<Question>()
                    val listQuestion = parseList(rowParser)
                    listQuestion.forEach {
                        listeScores.add(it.id to it.question)
                    }
                }
            } catch (exception: Exception) {
                Log.d("sqlite", exception.message)
            }
        }
        return listeScores
    }
}
