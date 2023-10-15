package com.example.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Context
import android.content.SharedPreferences
import androidx.recyclerview.widget.DividerItemDecoration

class MainActivity : AppCompatActivity() {

    private lateinit var todoAdapter: TodoAdapter
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        todoAdapter = TodoAdapter(mutableListOf(), sharedPreferences)

        val layoutManager = LinearLayoutManager(this)
        val itemDecorator = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        rvTodoItems.apply {
            adapter = todoAdapter
            this.layoutManager = layoutManager
            addItemDecoration(itemDecorator)
            setHasFixedSize(true)
        }

        todoAdapter.loadData()

        btnAddTodo.setOnClickListener {
            val todoTitle = etTodoTitle.text.toString()
            if (todoTitle.isNotEmpty()) {
                val todo = Todo(todoTitle)
                todoAdapter.addTodo(todo)
                etTodoTitle.text.clear()

                todoAdapter.saveData()
            }
        }

        btnDeleteDoneTodo.setOnClickListener {
            todoAdapter.deleteDoneTodos()

            todoAdapter.saveData()
        }
    }
}
