package fr.klin.ft_hangouts.fragments

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.telephony.SmsManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import fr.klin.ft_hangouts.DataMessage
import fr.klin.ft_hangouts.MainActivity
import fr.klin.ft_hangouts.R
import fr.klin.ft_hangouts.adapter.MessageAdapter
import fr.klin.ft_hangouts.model.MessageModel


class MessageFragment(
    private val context: MainActivity,
    private val phoneNumber: String
) : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_message, container, false)

        //get all messages ans set recyclerView
        val db = DataMessage(context)
        val messageList = db.getAllMessage(phoneNumber)
        val transaction = fragmentManager?.beginTransaction()
        val messageRecyclerView = view.findViewById<RecyclerView>(R.id.recycler_message)
        val messageAdapter = MessageAdapter(messageList, context, transaction)
        messageRecyclerView.adapter = messageAdapter


        //set actionBar, back button
        val actionBar = context.supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(false)

        val strText = view.findViewById<EditText>(R.id.text_message)
        val buttonSend = view.findViewById<Button>(R.id.button_send)

        buttonSend.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(context,
                    android.Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED
                && strText != null) {
                val sms: String = strText.getText().toString()
                val smsManager = SmsManager.getDefault()
                smsManager.sendTextMessage(phoneNumber, null, sms, null, null)
                val message = MessageModel(null, phoneNumber, 1, "", sms)
                db.addMessage(message)
                messageAdapter.updateData(message)
                strText.setText("")
            }
        }

        return view
    }
}