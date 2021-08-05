package fr.klin.ft_hangouts.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import fr.klin.ft_hangouts.MainActivity
import fr.klin.ft_hangouts.R
import fr.klin.ft_hangouts.fragments.ContactAddFragment
import fr.klin.ft_hangouts.fragments.ContactInfoFragment
import fr.klin.ft_hangouts.model.ContactModel

class ContactAdapter(
    private val contactList: List<ContactModel>,
    private val context: MainActivity,
    private val transaction: FragmentTransaction?
) : RecyclerView.Adapter<ContactAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val contactName: TextView = view.findViewById(R.id.contact_name)
        val contactNumber: TextView = view.findViewById(R.id.contact_number)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_vertical_contact, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentContact = contactList[position]
        holder.contactName.text = currentContact.first_name + " " + currentContact.last_name
        holder.contactNumber.text = currentContact.number
        holder.itemView.setOnClickListener {
            transaction?.replace(R.id.fragment_container, ContactInfoFragment(context, currentContact?.id))
            transaction?.addToBackStack(null)
            transaction?.commit()
        }
    }

    override fun getItemCount(): Int {
        return contactList.size
    }
}