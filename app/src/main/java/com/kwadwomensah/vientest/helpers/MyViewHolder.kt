package com.kwadwomensah.vientest.helpers

import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kwadwomensah.vientest.R

class MyViewHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
    private val TAG = "MyViewHolder";

    var view: View = v
    var titleText: TextView? = null
    var bodyText: TextView? = null

    init {
        titleText = view.findViewById(R.id.dataTitle)
        bodyText = view.findViewById(R.id.dataBody)
        v.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val pos = adapterPosition
    }
}
