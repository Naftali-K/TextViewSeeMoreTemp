package com.nk.textviewseemoretemp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView

class NewActivity : AppCompatActivity() {

    private lateinit var textView: TextView
    private lateinit var updateTextBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new)
//        setContentView(R.layout.activity_main)
        setReferences()

        updateTextBtn.setOnClickListener {
//            textView.text = getString(R.string.long_text)
            setShowMoreView("Updated text" + getString(R.string.long_text), textView)
        }

        ShowMoreHelper
            .Builder(textView.context)
            .setOnClickListener {
                Log.i("TAG", "onCreate: NewActivity 28: Clicked on text")
            }
//            .setOnClickListener {  }
//            .setOnClickListener {
//                listener.invoke(getString(R.string.long_text))
//            }
            .build().apply {
                addShowMoreLess(
                    textView = textView,
                    text = getString(R.string.long_text),
                    isContentExpanded = false    // Boolean content expanded
                )
            }
    }

    fun setReferences() {
        textView = findViewById(R.id.text_view);
        updateTextBtn = findViewById(R.id.updateTextBtn)
    }

    private fun setShowMoreView(message: String, view: TextView) {
//        ShowMoreHelper
//            .Builder(itemView.context)
//            .setOnClickListener {
//
//            }
//            .setOnClickListener {
//                listener.invoke(message)
//            }
//            .build().apply {
//                addShowMoreLess(
//                    textView = itemView.messageView,
//                    text = itemView.messageView.text,
//                    isContentExpanded = false    // Boolean content expanded
//                )
//            }

        ShowMoreHelper
            .Builder(view.context)
//            .setOnClickListener {
//
//            }.setOnClickListener {  }
//            .setOnClickListener {
//                listener.invoke(getString(R.string.long_text))
//            }
            .build().apply {
                addShowMoreLess(
                    textView = view,
                    text = message,
                    isContentExpanded = false    // Boolean content expanded
                )
            }
    }
}