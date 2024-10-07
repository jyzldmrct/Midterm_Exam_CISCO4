package ph.edu.auf.dimarucut.jayzel.midterm_exam_cisco_4

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import android.widget.Toast
import android.app.AlertDialog
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import ph.edu.auf.dimarucut.jayzel.midterm_exam_cisco_4.Adapter.Task
import ph.edu.auf.dimarucut.jayzel.midterm_exam_cisco_4.Adapter.TaskAdapter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var taskAdapter: TaskAdapter
    private val tasks = mutableListOf<Task>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        taskAdapter = TaskAdapter(tasks)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = taskAdapter

        setupSwipeToDelete()
        setupAddButton()
    }

    private fun setupSwipeToDelete() {
        val swipeHandler = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val removedTask = tasks[position]
                tasks.removeAt(position)
                taskAdapter.notifyItemRemoved(position)

                Snackbar.make(recyclerView, "Task Deleted", Snackbar.LENGTH_LONG)
                    .setAction("Undo") {
                        tasks.add(position, removedTask)
                        taskAdapter.notifyItemInserted(position)
                    }.show()
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun setupAddButton() {
        val addButton: Button = findViewById(R.id.addButton)
        val taskInput: EditText = findViewById(R.id.taskInput)

        addButton.setOnClickListener {
            val taskName = taskInput.text.toString()
            if (TextUtils.isEmpty(taskName)) {
                Toast.makeText(this, "Task cannot be empty", Toast.LENGTH_SHORT).show()
            } else {
                AlertDialog.Builder(this)
                    .setTitle("Confirm Add Task")
                    .setMessage("Are you sure you want to add this task?")
                    .setPositiveButton("Yes") { _, _ ->
                        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                        val task = Task(taskName, currentDate)
                        tasks.add(task)
                        taskAdapter.notifyItemInserted(tasks.size - 1)
                        taskInput.text.clear()
                    }
                    .setNegativeButton("No", null)
                    .show()
            }
        }
    }

    private fun showAddTaskDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_task, null)
        val editText = dialogView.findViewById<EditText>(R.id.taskNameEditText)

        val alertDialog = AlertDialog.Builder(this)
            .setTitle("Add Task")
            .setView(dialogView)
            .setPositiveButton("Add") { _, _ ->
                val taskName = editText.text.toString()
                if (TextUtils.isEmpty(taskName)) {
                    Toast.makeText(this, "Task cannot be empty", Toast.LENGTH_SHORT).show()
                } else {
                    val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                    val task = Task(taskName, currentDate)
                    tasks.add(task)
                    taskAdapter.notifyItemInserted(tasks.size - 1)
                }
            }
            .setNegativeButton("Cancel", null)
            .create()
        alertDialog.show()
    }
}