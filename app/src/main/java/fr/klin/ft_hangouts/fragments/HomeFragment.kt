package fr.klin.ft_hangouts.fragments

import android.os.Bundle
import android.view.*
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
        setHasOptionsMenu(true)
        actionBar?.setDisplayHomeAsUpEnabled(false)
        actionBar?.title = "FT_Hangouts"

        val buttonAdd = view.findViewById<ImageButton>(R.id.b_add)
            buttonAdd.setOnClickListener {
            transaction?.replace(R.id.fragment_container, ContactAddFragment(context, null, getString(R.string.add)))
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

    // menu item action
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_header, menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_header_menu -> {
                val transaction = fragmentManager?.beginTransaction()
                transaction?.replace(R.id.fragment_container, HeaderFragment(context))
                transaction?.addToBackStack(null)
                transaction?.commit()
                true
            }
            else -> true
        }
    }

}