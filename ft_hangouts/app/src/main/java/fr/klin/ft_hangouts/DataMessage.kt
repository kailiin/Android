package fr.klin.ft_hangouts

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import fr.klin.ft_hangouts.model.ContactModel
import fr.klin.ft_hangouts.model.MessageModel

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
                + KEY_SEND + " INTEGER,"
                + KEY_NUMBER + " TEXT," + KEY_TIME + " TEXT,"
                + KEY_TEXT + " TEXT" + ")")
        db?.execSQL(CREATE_MESSAGE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun getAllMessage(number: String): List<MessageModel> {
        val lstMessage = arrayListOf<MessageModel>()
        val selection = "SELECT * FROM ${TABLE_NAME} WHERE $KEY_NUMBER=?"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selection, arrayOf(number))
        if (cursor.moveToFirst()) {
            do {
                val message = MessageModel()
                message.id = cursor.getLong(cursor.getColumnIndex(KEY_ID))
                message.number = cursor.getString(cursor.getColumnIndex(KEY_NUMBER))
                message.send = cursor.getInt(cursor.getColumnIndex(KEY_SEND))
                message.time = cursor.getString(cursor.getColumnIndex(KEY_TIME))
                message.text = cursor.getString(cursor.getColumnIndex(KEY_TEXT))
                lstMessage.add(message)
            } while (cursor.moveToNext())
        }
        db.close()
        return lstMessage
    }

    fun addMessage(message: MessageModel) {
        val db = this.writableDatabase
        val value = ContentValues().apply {
            put(KEY_NUMBER, message.number)
            put(KEY_SEND, message.send)
            put(KEY_TIME, message.time)
            put(KEY_TEXT, message.text)
        }
        db.insert(TABLE_NAME, null, value)
        db.close()
    }

}