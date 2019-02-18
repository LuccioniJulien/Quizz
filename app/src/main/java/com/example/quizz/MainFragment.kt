package com.example.quizz

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_main.view.*

class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        val name = view.nickname
        view.button_play.setOnClickListener {
            Intent(context, QuizActivity::class.java).apply {
                name.text.toString().apply {
                    if (this == "") {
                        putExtra("Nickname", "guest")
                    } else {
                        putExtra("Nickname", this)
                    }
                }
                name.text.clear()
                startActivity(this)
            }
        }
        return view
    }
}
