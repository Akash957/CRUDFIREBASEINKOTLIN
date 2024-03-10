package com.example.phoneappauth

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class TestAdpater(private var users: List<StudentModel>, private val context: Context,
    private val onStudentClickListener: SetOnStudentClickListener) : BaseAdapter() {

    override fun getCount(): Int {
        return users.size
    }
    override fun getItem(position: Int): Any {
        return users[position]
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    @SuppressLint("ViewHolder", "MissingInflatedId")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.item, parent, false)

        val name = view.findViewById<TextView>(R.id.name_text)
        val email = view.findViewById<TextView>(R.id.email_text)
        val password = view.findViewById<TextView>(R.id.password_text)
        val delete = view.findViewById<ImageView>(R.id.delete)
        val update = view.findViewById<ImageView>(R.id.edit)

        name.text = users[position].name
        email.text = users[position].email
        password.text=users[position].password


        delete.setOnClickListener {
            onStudentClickListener.onDeleteClick(users[position])
        }

        update.setOnClickListener {
            onStudentClickListener.onUpdateClick(users[position])
        }
        return view
    }
}

interface SetOnStudentClickListener {
    fun onUpdateClick(student: StudentModel)
    fun onDeleteClick(student: StudentModel)
}
