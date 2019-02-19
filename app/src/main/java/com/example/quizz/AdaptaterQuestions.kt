package com.example.quizz

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class QuestionsAdaptater(
    val questionsList: List<Pair<Long, String>>,
    val delete: (Long) -> Unit,
    val edit: (Long) -> Unit
) : RecyclerView.Adapter<QuestionsAdaptater.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        holder.name.text = questionsList[pos].second
        holder.btn.setOnClickListener {
            delete(questionsList[pos].first)
        }
        holder.btnEdit.setOnClickListener {
            edit(questionsList[pos].first)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_questions, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return questionsList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.name)
        val btn = itemView.findViewById<ImageButton>(R.id.button_supprimer)
        val btnEdit = itemView.findViewById<ImageButton>(R.id.button_editer)
    }

}