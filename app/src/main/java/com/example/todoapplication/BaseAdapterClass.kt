package com.example.todoapplication

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.todoapplication.databinding.ItemAdapterBinding

class BaseAdapterClass(var arrayList: ArrayList<ToDoEntity>, var activity : MainActivity ) : BaseAdapter() {
    override fun getCount(): Int {
        return arrayList.size
    }

    override fun getItem(position: Int): Any {
        return "Any Datatype"
    }

    override fun getItemId(position: Int): Long {
         return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
//        var initView = LayoutInflater.from(parent?.context).inflate(R.layout.item_adapter, parent, false)
        var initView = ItemAdapterBinding.inflate(activity.layoutInflater)
//        var tvDate: TextView = initView.findViewById(R.id.tvDate)
//
//
//        var tvTask: TextView = initView.findViewById(R.id.tvTask)

//       initView.tvDate.setText(arrayList[position].id.toString())
//       initView.tvTask.setText(arrayList[position].todoItem)

        initView.todo = arrayList[position]


        return initView.root
    }
}
