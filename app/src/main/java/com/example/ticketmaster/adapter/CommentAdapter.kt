package com.example.ticketmaster.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ticketmaster.R
import com.example.ticketmaster.model.Comment

class CommentAdapter(
    private val comments: List<Comment>
) : RecyclerView.Adapter<CommentAdapter.CommentHolder>() {

    class CommentHolder(
        textView: TextView
    ) : RecyclerView.ViewHolder(textView)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CommentHolder {

        val view =
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.comment_item,
                    parent,
                    false
                ) as TextView

        return CommentHolder(view)
    }

    override fun onBindViewHolder(
        holder: CommentHolder,
        position: Int
    ) {

        val comment =
            comments[position]

        (holder.itemView as TextView).text =
            "${comment.author}: ${comment.message}"
    }

    override fun getItemCount(): Int {
        return comments.size
    }
}