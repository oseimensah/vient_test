package com.kwadwomensah.vientest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class PostDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_detail)

        initViews()
    }

    private fun initViews() {
        // get intent data
        val title = intent.getStringExtra("title")
        val body = intent.getStringExtra("detail")

        val titleText = findViewById<TextView>(R.id.detailTitle).apply {
            text = title
        }
        val bodyText = findViewById<TextView>(R.id.detailBody).apply {
            text = body
        }
    }
}