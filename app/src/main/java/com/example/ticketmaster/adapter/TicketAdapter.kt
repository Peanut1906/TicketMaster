package com.example.ticketmaster.adapter

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ticketmaster.R
import com.example.ticketmaster.activities.TicketDetailActivity
import com.example.ticketmaster.model.Ticket

class TicketAdapter(
    private val tickets: List<Ticket>
) : RecyclerView.Adapter<TicketAdapter.TicketViewHolder>() {

    class TicketViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val title: TextView =
            view.findViewById(R.id.ticketTitle)

        val status: TextView =
            view.findViewById(R.id.ticketStatus)

        val assigned: TextView =
            view.findViewById(R.id.ticketAssigned)

        val priority: TextView =
            view.findViewById(R.id.ticketPriority)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TicketViewHolder {

        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.ticket_item, parent, false)

        return TicketViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: TicketViewHolder,
        position: Int
    ) {

        val ticket = tickets[position]

        holder.title.text = ticket.title
        holder.status.text = ticket.status
        holder.assigned.text = "Assigned: ${ticket.assignedUser}"
        holder.priority.text = "Priority: ${ticket.priority}"

        holder.priority.setTextColor(
            when (ticket.priority) {
                "HIGH" -> Color.parseColor("#CC241D")
                "MEDIUM" -> Color.parseColor("#D79921")
                "LOW" -> Color.parseColor("#98971A")
                else -> Color.WHITE
            }
        )

        holder.itemView.setOnClickListener {
            val intent =
                Intent(holder.itemView.context, TicketDetailActivity::class.java)

            intent.putExtra("ticketId", ticket.id)

            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = tickets.size
}