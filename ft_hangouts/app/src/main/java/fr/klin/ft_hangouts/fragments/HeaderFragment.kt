package fr.klin.ft_hangouts.fragments

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import fr.klin.ft_hangouts.MainActivity
import fr.klin.ft_hangouts.R

class HeaderFragment(private val context: MainActivity): Fragment() {

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_header, container, false)

        val headerP = view.findViewById<Button>(R.id.header_p)
        val headerB = view.findViewById<Button>(R.id.header_b)
        val headerG = view.findViewById<Button>(R.id.header_g)
        val headerR = view.findViewById<Button>(R.id.header_r)

        val actionBar = context.supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val window = context.window
        window!!.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

        headerP.setOnClickListener {
            window.statusBarColor = Color.parseColor("#FF6200EE")
            val colorDrawable = ColorDrawable(Color.parseColor("#FF6200EE"))
            actionBar?.setBackgroundDrawable(colorDrawable)
        }
        headerB.setOnClickListener {
            window.statusBarColor = Color.BLUE
            val colorDrawable = ColorDrawable(Color.BLUE)
            actionBar?.setBackgroundDrawable(colorDrawable)
        }
        headerG.setOnClickListener {
            window.statusBarColor = Color.GREEN
            val colorDrawable = ColorDrawable(Color.GREEN)
            actionBar?.setBackgroundDrawable(colorDrawable)
        }
        headerR.setOnClickListener {
            window.statusBarColor = Color.RED
            val colorDrawable = ColorDrawable(Color.RED)
            actionBar?.setBackgroundDrawable(colorDrawable)
        }

        return view
    }
}