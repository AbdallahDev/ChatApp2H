package com.jhr.abdallahsarayrah.chatapp2h

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    @SuppressLint("Recycle")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val list = ArrayList<RecyclerModel>()
        val dbr = FirebaseDatabase.getInstance().reference

        val obj = ChatDB(this)
        val db = obj.writableDatabase
        val cursor = db.rawQuery("select * from chat", null)
        cursor.moveToFirst()
        while (!cursor.isAfterLast) {
            list.add(RecyclerModel(cursor.getString(0), cursor.getString(1)))
            cursor.moveToNext()
        }

        val adp = RecyclerAdapter(this, list)
        recyclerView.adapter = adp
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.scrollToPosition(list.size - 1)

        dbr.child("chatApp").child("abdallah").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
            }

            override fun onDataChange(p0: DataSnapshot?) {
                if (p0!!.value != null) {
                    list.add(RecyclerModel("me", p0.value.toString()))
                    adp.notifyDataSetChanged()
                    db.execSQL("insert into chat values('me', ?)", arrayOf(p0.value.toString()))
                    recyclerView.scrollToPosition(list.size - 1)
                    p0.ref.removeValue()
                }
            }
        })

        button_send.setOnClickListener {
            list.add(RecyclerModel("yousef", editText_msg.text.toString()))
            adp.notifyDataSetChanged()
            db.execSQL("insert into chat values('yousef', ?)",
                    arrayOf(editText_msg.text.toString()))
            dbr.child("chatApp").child("yousef").setValue(editText_msg.text.toString())
            recyclerView.scrollToPosition(list.size - 1)
            editText_msg.setText("")

            val mBuilder = NotificationCompat.Builder(this)
            mBuilder.setSmallIcon(R.mipmap.ic_launcher_round)
            mBuilder.setContentTitle("Msg")
            mBuilder.setContentText(editText_msg.text.toString())
            val mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE)
                    as NotificationManager
            mNotificationManager.notify(1, mBuilder.build())

            Toast.makeText(this, "msg", Toast.LENGTH_SHORT).show()
        }
    }
}
