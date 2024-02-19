package com.example.todoapplication

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.todoapplication.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var todoEntityList = arrayListOf<ToDoEntity>()
    var baseAdapterClass: BaseAdapterClass = BaseAdapterClass(todoEntityList, this)
    lateinit var todoDatabase: ToDoDatabase
    var simpleDateFormat = SimpleDateFormat("dd/MM/YYYY, hh:mm:ss")
    var calender = Calendar.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        todoDatabase = ToDoDatabase.getDatabaseIntance(this)

        getDatabaseValue()

        binding.listView.adapter = baseAdapterClass

        binding.listView.setOnItemClickListener { parent, view, position, id ->
             var dialog = Dialog(this)
            dialog.setContentView(R.layout.dialog_box)
            var etDate: EditText = dialog.findViewById(R.id.etDate)
            var etTime: EditText = dialog.findViewById(R.id.etTime)
            var etTask: EditText = dialog.findViewById(R.id.etTask)
            var btnUpdate: Button = dialog.findViewById(R.id.btnUpdate)
            etDate.setText(simpleDateFormat.format(todoEntityList[position].dateTime))
            etTask.setText(todoEntityList[position].todoItem)
            etDate.setOnClickListener {

                DatePickerDialog(this,{ _, year, month, dayofmonth,->
                        calender.set(Calendar.YEAR,year)
                        calender.set(Calendar.MONTH,month)
                        calender.set(Calendar.DAY_OF_MONTH,month)
                   var formattedDate = simpleDateFormat.format(calender.time)
                   etDate.setText(formattedDate)

               },

               calender.get(Calendar.YEAR),
               calender.get(Calendar.MONTH),
               calender.get(Calendar.DAY_OF_MONTH)).show()
            }

            etTime.setOnClickListener {

                TimePickerDialog(this,{_,hour, mintue->
                    calender.set(Calendar.HOUR,hour)
                    calender.set(Calendar.MINUTE,mintue)

                    var formattedDate = simpleDateFormat.format(calender.time)
                    etDate.setText(formattedDate)

                },
                    calender.get(Calendar.HOUR_OF_DAY),
                    calender.get(Calendar.MINUTE),true).show()
            }
            btnUpdate.setOnClickListener {
                if (etDate.text.toString().trim().isNullOrEmpty()) {
                    etDate.error = "Please enter something"
                } else if (etTask.text.toString().trim().isNullOrEmpty()) {
                    etTask.error = "Please enter something"
                } else {
                    todoDatabase.todoDao().updateToDoEntity(ToDoEntity(todoItem = etTask.text.toString(),
                        id = todoEntityList[position].id))
                    getDatabaseValue()
                    dialog.cancel()
                }
            }
            dialog.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            dialog.show()
        }
        binding.listView.setOnItemLongClickListener { parent, view, position, id ->

            AlertDialog.Builder(this)
                .setTitle(resources.getString(R.string.delete_message))
                .setPositiveButton("yes") { _, _ ->
                    todoDatabase.todoDao().deleteToDoEntity(todoEntityList[position])
                   getDatabaseValue()
                    Toast.makeText(this, resources.getString(R.string.removed), Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("No") { _, _ ->
                    Toast.makeText(this, "Can't remove ", Toast.LENGTH_SHORT).show()
                }
                .show()

            return@setOnItemLongClickListener true
        }



        binding.fab.setOnClickListener {
            var dialog = Dialog(this)
            dialog.setContentView(R.layout.dialog_box)
            var etDate: EditText = dialog.findViewById(R.id.etDate)
            var etTime: EditText = dialog.findViewById(R.id.etTime)
            var etTask: EditText = dialog.findViewById(R.id.etTask)
            var btnUpdate: Button = dialog.findViewById(R.id.btnUpdate)

            etDate.setOnClickListener {

                DatePickerDialog(this,{ _, year, month, dayofmonth,->
                    calender.set(Calendar.YEAR,year)
                    calender.set(Calendar.MONTH,month)
                    calender.set(Calendar.DAY_OF_MONTH,month)
                    var formattedDate = simpleDateFormat.format(calender.time)
                    etDate.setText(formattedDate)

                },

                    calender.get(Calendar.YEAR),
                    calender.get(Calendar.MONTH),
                    calender.get(Calendar.DAY_OF_MONTH)).show()
            }

            etTime.setOnClickListener {

                TimePickerDialog(this,{_,hour, mintue->
                    calender.set(Calendar.HOUR,hour)
                    calender.set(Calendar.MINUTE,mintue)

                    var formattedDate = simpleDateFormat.format(calender.time)
                    etTime.setText(formattedDate)

                },
                    calender.get(Calendar.HOUR_OF_DAY),
                    calender.get(Calendar.MINUTE),true).show()
            }

            btnUpdate.setOnClickListener {
                if (etDate.text.toString().trim().isNullOrEmpty()) {
                    etDate.error = "Please enter something"
                } else if (etTask.text.toString().trim().isNullOrEmpty()) {
                    etTask.error = "Please enter something"
                } else {
                    todoDatabase.todoDao().insertToDO(ToDoEntity(todoItem = etTask.text.toString()))
                   getDatabaseValue()
                    dialog.cancel()
                }
            }
            dialog.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            dialog.show()
        }
    }

    fun getDatabaseValue() {
        todoEntityList.clear()
        todoEntityList.addAll(todoDatabase.todoDao().getTodoEntities())
        baseAdapterClass.notifyDataSetChanged()
    }

}