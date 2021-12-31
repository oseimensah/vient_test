package com.kwadwomensah.vientest.adapter

import com.kwadwomensah.vientest.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kwadwomensah.vientest.helpers.CellClickListener
import com.kwadwomensah.vientest.helpers.MyViewHolder
import com.kwadwomensah.vientest.model.PostModel
import java.util.*


class MyRecyclerAdapter(private val c: Context, private val posts: ArrayList<PostModel>,
                        private val cellClickListener: CellClickListener
) : RecyclerView.Adapter<MyViewHolder>() {

    //INITIALIZE VH
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.post_item, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //BIND DATA
        holder.titleText?.setText(posts[position].title)
        holder.bodyText?.setText(posts[position].body)
        holder.itemView.setOnClickListener{
            cellClickListener.onCellClickListener(posts[position])
        }
    }

    //TOTAL NUM
    override fun getItemCount(): Int {
        return posts.size
    }

}
