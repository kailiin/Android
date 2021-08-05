package fr.klin.ft_hangouts

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataMessage (context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        const val DATABASE_NAME = "message.db"
        const val DATABASE_VERSION = 1

        // Table
        private const val TABLE_NAME = "Message"
        private const val KEY_ID = "id"
        private const val KEY_NUMBER = "number"
        private const val KEY_SEND = "send"
        private const val KEY_TIME = "time"
        private const val KEY_TEXT = "text"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_MESSAGE_TABLE =  ("CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_SEND + " TEXT,"
                + KEY_NUMBER + " TEXT," + KEY_TIME + " TEXT,"
                + KEY_TEXT + " TEXT," + ")")
        db?.execSQL(CREATE_MESSAGE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun getMessage(number: String) {
        
    }
}