package com.jhr.abdallahsarayrah.chatapp2h

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.sent_msg_layout.view.*

/**
 * Created by abdallah.sarayrah on 1/24/2018.
 */
class RecyclerAdapter(private var context: Context, private var list: ArrayList<RecyclerModel>) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemViewType(position: Int): Int {
        return if (list[position].name == "me") 1
        else 2
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 1) {
            val v = LayoutInflater.from(context).inflate(R.layout.sent_msg_layout, parent,
                    false)
            MsgView(v)
        } else {
            val v = LayoutInflater.from(context).inflate(R.layout.received_msg_layout, parent,
                    false)
            MsgView(v)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        (holder as MsgView).bind(list[position].msg)
    }

    class MsgView(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(m: String) {
            itemView.textView_msg.text = m
        }
    }
}