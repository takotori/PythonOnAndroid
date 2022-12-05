package com.example.pythonOnAndroid.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pythonOnAndroid.LeaderboardAdapter
import com.example.pythonOnAndroid.R
import com.example.pythonOnAndroid.databinding.ActivityScoreBinding
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class ScoreActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityScoreBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val url = getString(R.string.dbURL)
        FirebaseDatabase.getInstance(url).getReference("leaderboard").get().addOnSuccessListener {
            val map = sortedMapOf<String, Long>()
            it.children.forEach { data -> map[data.key.toString()] = data.value as Long }
            createLeaderboard(map)
        }
    }

    private fun createLeaderboard(map: SortedMap<String, Long>) {
        val sortedLeaderboard = map.toList().sortedBy { (_, value) -> value }.toList().reversed()
        binding.leaderboard.adapter = LeaderboardAdapter(this, sortedLeaderboard)
        binding.leaderboard.layoutManager = LinearLayoutManager(this)
    }
}