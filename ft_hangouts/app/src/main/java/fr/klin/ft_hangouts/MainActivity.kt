package fr.klin.ft_hangouts

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.provider.Telephony
import android.telephony.SmsMessage
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import fr.klin.ft_hangouts.fragments.HomeFragment
import fr.klin.ft_hangouts.fragments.MessageFragment
import fr.klin.ft_hangouts.model.ContactModel
import fr.klin.ft_hangouts.model.MessageModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class MainActivity : AppCompatActivity(){

    private var pauseTime: String = ""
    private val PERMISSIONS = arrayOf(
        android.Manifest.permission.CALL_PHONE,
        android.Manifest.permission.READ_SMS,
        android.Manifest.permission.RECEIVE_SMS,
        android.Manifest.permission.SEND_SMS
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //check all permission and BroadcastReceiver()
        checkPermissions()
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED)
            receiveMsg()

        //Fragment transaction
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, HomeFragment(this))
        transaction.commit()
    }

    private fun checkPermissions() {
        var result: Int
        val listPermissionsNeeded: MutableList<String> = ArrayList()
        for (p in PERMISSIONS) {
            result = ContextCompat.checkSelfPermission(applicationContext, p)
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p)
            }
        }
        if (listPermissionsNeeded.isNotEmpty())
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toTypedArray(), 111)
    }

    private fun receiveMsg() {
        val dbContact = DataBaseHelper(this)
        val dbMessage = DataMessage(this)
        var br = object: BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (intent?.action.equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) {
//                    var bundle = intent?.extras
//                    if (bundle != null) {
//                        val pdusObj = bundle["pdus"] as Array<Any>?
//                        for (i in pdusObj!!.indices) {
//                            val currentMessage: SmsMessage =
//                                SmsMessage.createFromPdu(pdusObj[i] as ByteArray)
//                            val phoneNumber: String = currentMessage.getDisplayOriginatingAddress()
//                            val message: String = currentMessage.getDisplayMessageBody()
//                            println("SmsReceiver senderNum: $phoneNumber; message: $message")
//                        }
//                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        for (sms in Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                            val phone = sms.displayOriginatingAddress
                            val smsStr = sms.displayMessageBody
                            var lastFragment = supportFragmentManager.fragments.last()
                            if (!dbContact.numberExistence(phone)) {
                                val newContact = ContactModel(null, "", "", phone, null, null, null)
                                dbContact.addContact(newContact)
                                if (lastFragment is HomeFragment && lastFragment.isVisible)
                                    lastFragment.refresh()
                            }
                            val message = MessageModel(null,  phone, 0, "", smsStr)
                            dbMessage.addMessage(message)
                            if (lastFragment is MessageFragment && lastFragment.isVisible)
                                lastFragment.refreshMessage(phone, message)
                        }
                    }
                }
            }
        }
        registerReceiver(br, IntentFilter("android.provider.Telephony.SMS_RECEIVED"))
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed();
        return true;
    }

    override fun onBackPressed() {
        val fm = supportFragmentManager

        if (fm.backStackEntryCount > 0)
            fm.popBackStack()
        else
            super.onBackPressed()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onPause(){
        val currentDateTime = LocalDateTime.now()
        pauseTime = currentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
        super.onPause()
    }

    override fun onResume(){
        if (pauseTime.isNotEmpty())
            Toast.makeText(this, pauseTime, Toast.LENGTH_LONG).show()
        pauseTime = ""
        super.onResume()
    }

}

