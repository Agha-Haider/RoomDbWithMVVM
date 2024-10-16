package com.example.roomdbwithmvvm.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdbwithmvvm.R
import com.example.roomdbwithmvvm.databinding.ListItemsBinding
import com.example.roomdbwithmvvm.db.Subscriber

interface OnCLickListener{
    fun onClick(subscriber: Subscriber)
}
class CustomAdapter(private val onCLickListener: OnCLickListener):RecyclerView.Adapter<CustomAdapter.viewHolder>() {

    private val subscriberList: ArrayList<Subscriber> = ArrayList()

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
    fun setList(subscriber: List<Subscriber>){
        subscriberList.clear()
        subscriberList.addAll(subscriber)
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
