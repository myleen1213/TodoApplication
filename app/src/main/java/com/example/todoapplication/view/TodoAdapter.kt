package com.example.todoapplication.view
import android.app.AlertDialog
import android.content.Context
import android.service.autofill.UserData
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

import com.example.todoapplication.R
import com.example.todoapplication.model.ToDo
import kotlinx.android.synthetic.main.list_item.view.*

//reads into the ToDo.kt file

class TodoAdapter (val c:Context, val userList:ArrayList<ToDo>):RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {


    inner class TodoViewHolder(val v: View) : RecyclerView.ViewHolder(v) {
        var task: TextView
        var mMenus: ImageView

        init {
            task = v.findViewById<TextView>(R.id.tvTitle)
            mMenus = v.findViewById(R.id.mMenus)
            mMenus.setOnClickListener { popupMenus(it) }
        }

        //ACTIVATES MY POP UP MENU
        private fun popupMenus(v: View) {
            //CHANGED FROM [adapterPosition]
            val position = userList[absoluteAdapterPosition]
            // public final int getAdapterPosition()
            val popupMenus = PopupMenu(c, v)
            popupMenus.inflate(R.menu.show_menu)


            //CONNECT MY EDIT TEXT BUTTON
            popupMenus.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.editText -> {
                        val v = LayoutInflater.from(c).inflate(R.layout.add_item, null)
                        val title = v.findViewById<EditText>(R.id.usertask)
                        AlertDialog.Builder(c)
                            .setView(v)
                            .setPositiveButton("Ok") { dialog, _ ->
                                //THIS IS SUPPOSE TO BE USER TASK
                                position.Title = title.text.toString()
                                notifyDataSetChanged()
                                Toast.makeText(c, "Task Updated!", Toast.LENGTH_SHORT).show()
                                dialog.dismiss()
                            }
                            .setNegativeButton("Cancel") { dialog, _ ->
                                dialog.dismiss()

                            }
                            .create()
                            .show()

                        true
                    }

                    //CONNECTS MY DELETE BUTTON
                    R.id.delete -> {
                        //set delete
                        AlertDialog.Builder(c)
                            .setTitle("Delete")
                            .setIcon(R.drawable.ic_warning)
                            .setMessage("Are you sure you want to delete this task? ")
                            .setPositiveButton("Yes") { dialog, _ ->
                                userList.removeAt(absoluteAdapterPosition)
                                notifyDataSetChanged()
                                Toast.makeText(c, "Task deleted", Toast.LENGTH_SHORT).show()
                                dialog.dismiss()
                            }
                            .setNegativeButton("No") { dialog, _ ->
                                dialog.dismiss()
                            }
                            .create()
                            .show()

                        true
                    }
                    else -> true
                }

            }
            popupMenus.show()
            val popup = PopupMenu::class.java.getDeclaredField("mPopup")
            popup.isAccessible = true
            val menu = popup.get(popupMenus)
            menu.javaClass.getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                .invoke(menu, true)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.list_item, parent, false)
        return TodoViewHolder(v)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val newList = userList[position]
        holder.task.text = newList.Title
    }

    //return the size of the user list created in the todoAdapter
    override fun getItemCount(): Int {
        return userList.size
    }

}



