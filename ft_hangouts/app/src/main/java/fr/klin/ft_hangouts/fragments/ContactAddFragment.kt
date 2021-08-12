package fr.klin.ft_hangouts.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import fr.klin.ft_hangouts.DataBaseHelper
import fr.klin.ft_hangouts.MainActivity
import fr.klin.ft_hangouts.R
import fr.klin.ft_hangouts.model.ContactModel


class ContactAddFragment(
    private val context: MainActivity,
    private val contact: ContactModel?
    ): Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_contact_add, container, false)
        val db = DataBaseHelper(context)

        val strFname = view.findViewById<EditText>(R.id.first_name)
        val strLname = view.findViewById<EditText>(R.id.last_name)
        val strNumber = view.findViewById<EditText>(R.id.number)
        val strEmail = view.findViewById<EditText>(R.id.email)
        val strAdrress = view.findViewById<EditText>(R.id.address)
        val strNote = view.findViewById<EditText>(R.id.note)
        val strWarning = view.findViewById<TextView>(R.id.warning)
        val button_cancel = view.findViewById<Button>(R.id.b_cancel)
        val button_save = view.findViewById<Button>(R.id.b_save)

        val actionBar = context.supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        if (contact != null) {
            strFname.setText(contact.first_name)
            strLname.setText(contact.last_name)
            strNumber.setText(contact.number)
            strEmail.setText(contact.email)
            strAdrress.setText(contact.address)
            strNote.setText(contact.note)
        }

        button_cancel.setOnClickListener {
           popBack()
        }

        button_save.setOnClickListener {
            if (strNumber.text.isNotEmpty()) {
                val newContact = ContactModel(
                    null,
                    strFname.text.toString(),
                    strLname.text.toString(),
                    strNumber.text.toString(),
                    strEmail.text.toString(),
                    strAdrress.text.toString(),
                    strNote.text.toString(),
                )
                if (contact != null) {
                    if (newContact.number == contact.number || !db.numberExistence(strNumber.toString())) {
                        contact.id?.let { it -> db.updateContact(newContact, it) }
                        popBack()
                    } else
                        strWarning.text = getString(R.string.error_exist)
                }
                else if (!db.numberExistence(strNumber.toString())) {
                    db.addContact(newContact)
                    popBack()
                } else
                    strWarning.text = getString(R.string.error_exist)
            }
            else {
                strWarning.text = getString(R.string.error_number)
            }
        }

        return view
    }

    private fun popBack() {
        val fm = fragmentManager
        fm?.popBackStack()
    }

}