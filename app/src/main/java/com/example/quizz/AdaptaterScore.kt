package com.example.quizz

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ScoreAdaptater(val userList: List<Pair<String, String>>) : RecyclerView.Adapter<ScoreAdaptater.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        holder.name.text = userList[pos].first
        holder.score.text = userList[pos].second
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_score, parent, false)
        return ViewHolder(v);
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.name)
        val score = itemView.findViewById<TextView>(R.id.score)
    }

}