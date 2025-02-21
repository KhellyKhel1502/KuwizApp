package com.example.kuwizzz

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserProgressAdapter(
    private val questionsList: ArrayList<Question>,
    private val userAnswers: ArrayList<String>
) : RecyclerView.Adapter<UserProgressAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val questionText: TextView = itemView.findViewById(R.id.questionText)
        val choiceA: TextView = itemView.findViewById(R.id.choiceA)
        val choiceB: TextView = itemView.findViewById(R.id.choiceB)
        val choiceC: TextView = itemView.findViewById(R.id.choiceC)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user_progress, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val question = questionsList[position]
        val userAnswer = userAnswers[position]

        holder.questionText.text = question.questionText
        holder.choiceA.text = question.choiceA
        holder.choiceB.text = question.choiceB
        holder.choiceC.text = question.choiceC

        holder.choiceA.setBackgroundColor(if (question.correctAnswer == "A") Color.GREEN else Color.TRANSPARENT)
        holder.choiceB.setBackgroundColor(if (question.correctAnswer == "B") Color.GREEN else Color.TRANSPARENT)
        holder.choiceC.setBackgroundColor(if (question.correctAnswer == "C") Color.GREEN else Color.TRANSPARENT)

        if (question.correctAnswer != userAnswer) {
            when (userAnswer) {
                "A" -> holder.choiceA.setBackgroundColor(Color.RED)
                "B" -> holder.choiceB.setBackgroundColor(Color.RED)
                "C" -> holder.choiceC.setBackgroundColor(Color.RED)
            }
        }
    }

    override fun getItemCount(): Int = questionsList.size
}
