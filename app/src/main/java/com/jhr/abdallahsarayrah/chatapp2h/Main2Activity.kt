package com.jhr.abdallahsarayrah.chatapp2h

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main2.*

class Main2Activity : AppCompatActivity() {

    @SuppressLint("Recycle")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

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

        dbr.child("chatApp").child("yousef").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
            }

            override fun onDataChange(p0: DataSnapshot?) {
                if (p0!!.value != null) {
                    list.add(RecyclerModel("mec", p0.value.toString()))
                    adp.notifyDataSetChanged()
                    db.execSQL("insert into chat values('yousef', ?)", arrayOf(p0.value.toString()))
                    recyclerView.scrollToPosition(list.size - 1)
                    p0.ref.removeValue()
                }
            }
        })

        button_send.setOnClickListener {
            list.add(RecyclerModel("abdallah", editText_msg.text.toString()))
            adp.notifyDataSetChanged()
            db.execSQL("insert into chat values('abdallah', ?)",
                    arrayOf(editText_msg.text.toString()))
            dbr.child("chatApp").child("abdallah").setValue(editText_msg.text.toString())
            recyclerView.scrollToPosition(list.size - 1)
            editText_msg.setText("")
        }
    }
}
