package fr.klin.ft_hangouts.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import fr.klin.ft_hangouts.DataBaseHelper
import fr.klin.ft_hangouts.MainActivity
import fr.klin.ft_hangouts.R
import java.net.URI

class ContactInfoFragment(
    private val context: MainActivity,
    private var contactID: Long?
    ): Fragment() {

    private val db = DataBaseHelper(context)
    lateinit var phoneNumber: String

    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_contact_info, container, false)

        val strName = view.findViewById<TextView>(R.id.info_contact)
        val strNumber = view.findViewById<TextView>(R.id.info_number)
        val strEmail = view.findViewById<TextView>(R.id.info_email)
        val strAddress = view.findViewById<TextView>(R.id.info_address)
        val strNote = view.findViewById<TextView>(R.id.info_note)
        val buttonCall = view.findViewById<ImageButton>(R.id.button_call)
        val buttonMessage = view.findViewById<ImageButton>(R.id.button_message)
        val buttonCancel = view.findViewById<Button>(R.id.info_b_cancel)
        val buttonModify = view.findViewById<Button>(R.id.info_b_modify)

        var contact = db.getContact(contactID.toString())
        strName.text = contact.first_name + " " + contact.last_name
        strNumber.text = contact.number.toString()
        strEmail.text = contact.email
        strAddress.text = contact.address
        strNote.text = contact.note

        // set action bar
        setHasOptionsMenu(true)
        val actionBar = context.supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.title = strNumber.text

        //set call button
        buttonCall.setOnClickListener {
            phoneNumber = strNumber.text.toString()
            if (ActivityCompat.checkSelfPermission(context,
                    android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED)
                callNumber()
            else
                Toast.makeText(context, getString(R.string.permission_call), Toast.LENGTH_LONG).show()
        }

        //set message button
        buttonMessage.setOnClickListener {
            phoneNumber = strNumber.text.toString()
            if (ActivityCompat.checkSelfPermission(context,
                    android.Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                val transaction = fragmentManager?.beginTransaction()
                transaction?.replace(R.id.fragment_container, MessageFragment(context, phoneNumber))
                transaction?.addToBackStack(null)
                transaction?.commit()
            } else
                Toast.makeText(context, getString(R.string.permission_sms), Toast.LENGTH_LONG).show()
        }

        //set cancel button
        buttonCancel.setOnClickListener {
            popBack()
        }

        //set modify button
        buttonModify.setOnClickListener {
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragment_container, ContactAddFragment(context, contact, getString(R.string.modify)))
            transaction?.addToBackStack(null)
            transaction?.commit()
        }

        return view
    }

    // call phone number
    fun callNumber() {
        val intent = Intent(Intent.ACTION_CALL)
        intent.data = Uri.parse("tel:${phoneNumber}")
        println("Result: " + Uri.parse("tel:${phoneNumber}"))
        startActivity(intent)
    }

    // set action bar menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
    }

    // menu item action
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_delect -> {
                contactID?.let { it -> db.delectContact(it) }
                popBack()
                true
            }
            R.id.home -> {
                popBack()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun popBack() {
        val fm = fragmentManager
        fm?.popBackStack()
    }
}