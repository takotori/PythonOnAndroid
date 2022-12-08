package com.example.pythonOnAndroid.activities.score

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pythonOnAndroid.Helper
import com.example.pythonOnAndroid.R
import com.example.pythonOnAndroid.db.ScoreDatabase
import com.example.pythonOnAndroid.databinding.ActivityScoreBinding
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch
import java.util.*

class ScoreActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityScoreBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val url = getString(R.string.dbURL)

        if (Helper.isAppOnline(applicationContext)) {
            FirebaseDatabase.getInstance(url).getReference("leaderboard").get()
                .addOnSuccessListener {
                    val map = sortedMapOf<String, Double>()
                    it.children.forEach { data -> map[data.key.toString()] = data.value as Double }
                    createLeaderboard(map)
                }
        } else {
            val dao = ScoreDatabase.getInstance(this).scoreDao
            lifecycleScope.launch {
                val allScoresFromDb = dao.getAll()
                val sorted = allScoresFromDb.associate { it.name to it.score }.toSortedMap()
                createLeaderboard(sorted)
            }
        }
    }

    private fun createLeaderboard(map: SortedMap<String, Double>) {
        val sortedLeaderboard = map.toList().sortedBy { (_, value) -> value }.toList().reversed()
        binding.leaderboard.adapter = LeaderboardAdapter(this, sortedLeaderboard)
        binding.leaderboard.layoutManager = LinearLayoutManager(this)
    }
}