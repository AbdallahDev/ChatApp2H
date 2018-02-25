package com.jhr.abdallahsarayrah.chatapp2h

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Created by abdallah.sarayrah on 1/24/2018.
 */
class ChatDB(context: Context) : SQLiteOpenHelper(context, "chat.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL("create table chat(name String, msg String)")
        db.execSQL("insert into chat values('me', 'abdallah')")
        db.execSQL("insert into chat values('yousef', 'yousef')")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
