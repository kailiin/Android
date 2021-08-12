package fr.klin.ft_hangouts.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import fr.klin.ft_hangouts.MainActivity
import fr.klin.ft_hangouts.R
import fr.klin.ft_hangouts.model.MessageModel


class MessageAdapter(
    private var myList: List<MessageModel>,
    private val context: MainActivity,
    private val transaction: FragmentTransaction?
    ) : RecyclerView.Adapter<MessageAdapter.ViewHolder>() {

    var messageList = myList.toMutableList()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val messageText: TextView = view.findViewById(R.id.text_sms)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_message, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentMessage = messageList[position]
        holder.messageText.text = currentMessage.text
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    fun updateData(sms: MessageModel) {
        messageList.add(sms)
        notifyDataSetChanged()
    }

}
