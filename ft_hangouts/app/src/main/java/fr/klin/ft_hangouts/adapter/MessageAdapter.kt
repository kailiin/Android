package fr.klin.ft_hangouts.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import fr.klin.ft_hangouts.MainActivity
import fr.klin.ft_hangouts.R
import fr.klin.ft_hangouts.model.MessageModel


class MessageAdapter(
    myList: List<MessageModel>,
    private val recyclerView: RecyclerView
    ) : RecyclerView.Adapter<MessageAdapter.ViewHolder>() {

    var messageList = myList.toMutableList()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val messageTextL: TextView = view.findViewById(R.id.text_sms_l)
        val messageContentL: CardView = view.findViewById(R.id.message_content_l)
        val messageTextR: TextView = view.findViewById(R.id.text_sms_r)
        val messageContentR: CardView = view.findViewById(R.id.message_content_r)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_message, parent, false)
        recyclerView.scrollToPosition(messageList.size - 1)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentMessage = messageList[position]
        if (currentMessage.send == 0) {
            holder.messageTextL.text = currentMessage.text
            holder.messageContentL.visibility = View.VISIBLE
        } else {
            holder.messageTextR.text = currentMessage.text
            holder.messageContentR.visibility = View.VISIBLE
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    fun updateData(sms: MessageModel) {
        messageList.add(sms)
        notifyDataSetChanged()
        recyclerView.scrollToPosition(messageList.size - 1)
    }

}
