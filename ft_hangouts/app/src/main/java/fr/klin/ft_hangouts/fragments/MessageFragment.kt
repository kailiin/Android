package fr.klin.ft_hangouts.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import fr.klin.ft_hangouts.DataBaseHelper
import fr.klin.ft_hangouts.MainActivity
import fr.klin.ft_hangouts.R
import fr.klin.ft_hangouts.adapter.ContactAdapter

class MessageFragmentFragment(
    private val context: MainActivity,
    private val number: String
) : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_message, container, false)

//        val db = DataBaseHelper(context)
//        val messageList = db.allContacts
        val transaction = fragmentManager?.beginTransaction()

        val verticalRecyclerView = view.findViewById<RecyclerView>(R.id.recycler_message)
        verticalRecyclerView.adapter = ContactAdapter(messagetList, context, transaction)

        val actionBar = context.supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(false)

        return view
    }
}