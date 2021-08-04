package fr.klin.ft_hangouts

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import fr.klin.ft_hangouts.fragments.HomeFragment
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class MainActivity : AppCompatActivity(){

    private var pauseTime: String = ""
    private val PERMISSIONS = arrayOf(
        android.Manifest.permission.CALL_PHONE,
        android.Manifest.permission.READ_SMS,
        android.Manifest.permission.SEND_SMS
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //check permission
        checkPermissions()

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

