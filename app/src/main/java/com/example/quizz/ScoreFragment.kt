package com.example.quizz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_score.view.*

class ScoreFragment : Fragment() {

    var listeScores:List<Pair<String,String>>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_score, container, false)
        val adapter = ScoreAdaptater(listeScores!!)
        view.rcViewScore.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        view.rcViewScore.adapter = adapter
        return view
    }

}
