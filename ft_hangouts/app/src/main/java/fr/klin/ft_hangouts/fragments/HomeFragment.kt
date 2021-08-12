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

class HomeFragment(private val context: MainActivity) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_home, container, false)

        val db = DataBaseHelper(context)
        val contactList = db.allContacts
        val transaction = fragmentManager?.beginTransaction()

        val verticalRecyclerView = view.findViewById<RecyclerView>(R.id.vertical_recycler_view)
        verticalRecyclerView.adapter = ContactAdapter(contactList, context, transaction)

        val actionBar = context.supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(false)

        val buttonAdd = view.findViewById<ImageButton>(R.id.b_add)
            buttonAdd.setOnClickListener {
            transaction?.replace(R.id.fragment_container, ContactAddFragment(context, null))
            transaction?.addToBackStack(null)
            transaction?.commit()
        }

        return view
    }

    fun refresh(){
        println(("refresh"))
        val transaction = fragmentManager?.beginTransaction()
        transaction?.detach(this)?.attach(this)?.commit()
    }

}