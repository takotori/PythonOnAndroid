package com.example.pythonOnAndroid.activities

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.pythonOnAndroid.R

private const val ARG_PARAM1 = "Score"

class Endscreen : Fragment() {
    private lateinit var callback: GameCallback
    private lateinit var quitBtn: Button
    private lateinit var restartBtn: Button
    private lateinit var scoreTxt: TextView
    var score: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            score = it.getInt(ARG_PARAM1)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            callback = context as GameCallback
        } catch (e: ClassCastException) {
            throw ClassCastException("Owner activity must implement Callback")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        quitBtn = view.findViewById(R.id.quitBtn)
        restartBtn = view.findViewById(R.id.restartBtn)
        scoreTxt = view.findViewById(R.id.scoreTxt)

        quitBtn.setOnClickListener {
            callback.quitGame()
        }

        restartBtn.setOnClickListener {
            callback.restartGame()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_endscreen, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(score: Int) =
            Endscreen().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, score)
                }
            }
    }
}