package com.example.pythonOnAndroid.activities

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.pythonOnAndroid.R
import com.example.pythonOnAndroid.databinding.FragmentEndscreenBinding

private const val ARG_PARAM1 = "Score"

class Endscreen : Fragment() {
    private lateinit var callback : GameCallback
    private var score: Int? = 0

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
        } catch (e : ClassCastException) {
            throw ClassCastException("Owner activity must implement Callback")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val quitBtn = view.findViewById<Button>(R.id.quitBtn)
        val restartBtn = view.findViewById<Button>(R.id.restartBtn)

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