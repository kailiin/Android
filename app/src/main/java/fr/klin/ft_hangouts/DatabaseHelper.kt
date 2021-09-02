package fr.klin.ft_hangouts

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import fr.klin.ft_hangouts.model.ContactModel

class DataBaseHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        const val DATABASE_NAME = "contact.db"
        const val DATABASE_VERSION = 1

        // Table
        private const val TABLE_NAME = "Contact"
        private const val KEY_ID = "id"
        private const val KEY_FNAME = "first_name"
        private const val KEY_LNAME = "last_name"
        private const val KEY_NUMBER = "number"
        private const val KEY_EMAIL = "email"
        private const val KEY_ADDRESS = "address"
        private const val KEY_NOTE = "note"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_CONTACTS_TABLE =  ("CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_FNAME + " TEXT," + KEY_LNAME + " TEXT,"
                + KEY_NUMBER + " TEXT," + KEY_EMAIL + " TEXT,"
                + KEY_ADDRESS + " TEXT," + KEY_NOTE + " TEXT" + ")")
        db?.execSQL(CREATE_CONTACTS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    val allContacts: List<ContactModel>
        get() {
            val lstContacts = arrayListOf<ContactModel>()
            val selection = "SELECT * FROM $TABLE_NAME"
            val db = this.writableDatabase
            val cursor = db.rawQuery(selection, null)
            if (cursor.moveToFirst()) {
                do {
                    val contact = ContactModel()
                    contact.id = cursor.getLong(cursor.getColumnIndex(KEY_ID))
                    contact.first_name = cursor.getString(cursor.getColumnIndex(KEY_FNAME))
                    contact.last_name = cursor.getString(cursor.getColumnIndex(KEY_LNAME))
                    contact.number = cursor.getString(cursor.getColumnIndex(KEY_NUMBER))
                    contact.email = cursor.getString(cursor.getColumnIndex(KEY_EMAIL))
                    contact.address = cursor.getString(cursor.getColumnIndex(KEY_ADDRESS))
                    contact.note = cursor.getString(cursor.getColumnIndex(KEY_NOTE))
                    lstContacts.add(contact)
                } while (cursor.moveToNext())
            }
            db.close()
            return lstContacts
        }

    fun addContact(contact: ContactModel) {
        val db = this.writableDatabase
        val value = ContentValues().apply {
            put(KEY_FNAME, contact.first_name)
            put(KEY_LNAME, contact.last_name)
            put(KEY_NUMBER, contact.number)
            put(KEY_EMAIL, contact.email)
            put(KEY_ADDRESS, contact.address)
            put(KEY_NOTE, contact.note)
        }
        db.insert(TABLE_NAME, null, value)
        db.close()
    }

    fun updateContact(contact: ContactModel, id: Long) {
        val db = this.writableDatabase
        val value = ContentValues().apply {
            put(KEY_FNAME, contact.first_name)
            put(KEY_LNAME, contact.last_name)
            put(KEY_NUMBER, contact.number)
            put(KEY_EMAIL, contact.email)
            put(KEY_ADDRESS, contact.address)
            put(KEY_NOTE, contact.note)
        }
        db.update(TABLE_NAME, value, "$KEY_ID=?", arrayOf(id.toString()))
        db.close()
    }

    fun delectContact(id: Long) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "$KEY_ID=?", arrayOf(id.toString()))
        db.close()
    }

    fun getContact(contactID: String): ContactModel {
        val selection = "SELECT * FROM $TABLE_NAME WHERE $KEY_ID=?"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selection, arrayOf(contactID))
        var contact = ContactModel()
        if (cursor.moveToFirst()) {
            contact.id = cursor.getLong(cursor.getColumnIndex(KEY_ID))
            contact.first_name = cursor.getString(cursor.getColumnIndex(KEY_FNAME))
            contact.last_name = cursor.getString(cursor.getColumnIndex(KEY_LNAME))
            contact.number = cursor.getString(cursor.getColumnIndex(KEY_NUMBER))
            contact.email = cursor.getString(cursor.getColumnIndex(KEY_EMAIL))
            contact.address = cursor.getString(cursor.getColumnIndex(KEY_ADDRESS))
            contact.note = cursor.getString(cursor.getColumnIndex(KEY_NOTE))
        }
        db.close()
        return contact
    }

    fun numberExistence(strPhone: String): Boolean {
        this.allContacts.forEach {
            if (it.number.toString() == strPhone)
                return true
        }
        return false
    }
}