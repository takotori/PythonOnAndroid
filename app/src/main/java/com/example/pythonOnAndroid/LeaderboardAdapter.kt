package com.example.pythonOnAndroid

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LeaderboardAdapter(
    private val context: Context,
    private val myList: List<Pair<String, Long>>
) : RecyclerView.Adapter<LeaderboardAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val myListItem = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(myListItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(myList[position], position + 1)
    }

    override fun getItemCount(): Int {
        return myList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(myItem: Pair<String, Long>, position: Int) {
            itemView.findViewById<TextView>(R.id.rank).text = position.toString()
            itemView.findViewById<TextView>(R.id.playerName).text = myItem.first
            itemView.findViewById<TextView>(R.id.score).text = myItem.second.toString()
        }
    }
}