package com.example.pythonOnAndroid.activities

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.pythonOnAndroid.R

class Endscreen(val score: Int) : DialogFragment() {
    private lateinit var callback: GameCallback

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val view = requireActivity().layoutInflater.inflate(R.layout.fragment_endscreen, null)

            val scoreTxt: TextView = view.findViewById(R.id.scoreTxt)
            scoreTxt.text = score.toString()

            builder.setView(view).setPositiveButton("Restart") { dialog, _ ->
                dialog?.cancel()
                callback.restartGame()
            }.setNegativeButton("Quit") { dialog, _ ->
                dialog?.cancel()
                callback.quitGame()
            }

            builder.create()
        } ?: throw IllegalStateException("Activity null")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            callback = context as GameCallback
        } catch (e: ClassCastException) {
            throw ClassCastException("Owner activity must implement Callback")
        }
    }
}