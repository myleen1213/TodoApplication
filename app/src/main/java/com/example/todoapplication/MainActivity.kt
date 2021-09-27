package com.example.todoapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.autofill.UserData
import android.service.controls.actions.FloatAction
import android.view.LayoutInflater
import android.widget.Adapter
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapplication.model.ToDo
import com.example.todoapplication.view.TodoAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var addsBtn:FloatingActionButton
    private lateinit var recv:RecyclerView
    private lateinit var userList:ArrayList<ToDo>
    private lateinit var todoAdapter:TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


                //Set list
                userList = ArrayList()
                //set find id
                addsBtn = findViewById(R.id.addingBtn)
                recv =findViewById(R.id.mRecycler)
                //set adapter
                todoAdapter = TodoAdapter(this,userList)
                //Set recyclerview adaoter
                recv.layoutManager = LinearLayoutManager(this)
                recv.adapter = todoAdapter
                //set Dialog
                addsBtn.setOnClickListener{addInfo()}
            }

            private fun addInfo() {
                val inflter = LayoutInflater.from(this)
                val v = inflter.inflate(R.layout.add_item,null)
                val addDialog = AlertDialog.Builder(this)

                //set view
                val usertask = v.findViewById<EditText>(R.id.usertask)
                addDialog.setView(v)
                addDialog.setPositiveButton("OK") {
                        dialog,_->
                    val tasks = usertask.text.toString()
                    userList.add(ToDo("Todo:  $tasks"))
                    todoAdapter.notifyDataSetChanged()
                    Toast.makeText(this,"Adding task successful!",Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
                addDialog.setNegativeButton("Cancel"){
                        dialog,_->
                    dialog.dismiss()
                    Toast.makeText(this,"Canceled",Toast.LENGTH_SHORT).show()
                }
                addDialog.create()
                addDialog.show()
            }
            //ok now
        }





