package com.example.roomdemo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdemo.R
import com.example.roomdemo.databinding.ListItemsBinding
import com.example.roomdemo.db.Subscriber

interface OnCLickListener{
    fun onClick(subscriber: Subscriber)
}
class CustomAdapter(private val subscriberList:List<Subscriber>,private val onCLickListener: OnCLickListener):RecyclerView.Adapter<CustomAdapter.viewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val layoutInflater=LayoutInflater.from(parent.context)
        val binding:ListItemsBinding=DataBindingUtil.inflate(layoutInflater,R.layout.list_items,parent,false)
        return viewHolder(binding)
     }

    override fun getItemCount(): Int {
       return subscriberList.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.onBind(subscriberList[position],onCLickListener)
    }


    class viewHolder(val binding: ListItemsBinding) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(subscriber: Subscriber,onCLickListener: OnCLickListener){
          binding.name.text=subscriber.name
            binding.email.text=subscriber.email

          binding.clickedLinear.setOnClickListener{
              onCLickListener.onClick(subscriber)
          }
        }

    }
}
