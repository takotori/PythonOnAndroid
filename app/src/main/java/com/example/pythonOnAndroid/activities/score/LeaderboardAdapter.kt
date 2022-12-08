package com.example.pythonOnAndroid.activities.score

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pythonOnAndroid.R

class LeaderboardAdapter(
    private val context: Context,
    private val leaderboard: List<Pair<String, Double>>
) : RecyclerView.Adapter<LeaderboardAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val entry = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(entry)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(leaderboard[position], position + 1)
    }

    override fun getItemCount(): Int {
        return leaderboard.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(leaderboardEntry: Pair<String, Double>, position: Int) {
            itemView.findViewById<TextView>(R.id.rank).text = position.toString()
            itemView.findViewById<TextView>(R.id.playerName).text = leaderboardEntry.first
            itemView.findViewById<TextView>(R.id.score).text = leaderboardEntry.second.toString()
        }
    }
}