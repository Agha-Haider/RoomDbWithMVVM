package com.example.roomdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdemo.adapter.CustomAdapter
import com.example.roomdemo.adapter.OnCLickListener
import com.example.roomdemo.databinding.ActivityMainBinding
import com.example.roomdemo.db.Subscriber
import com.example.roomdemo.db.SubscriberDatabase
import com.example.roomdemo.db.SubscriberRepository
import com.example.roomdemo.db.ViewModelProviderFactory

class MainActivity : AppCompatActivity(),OnCLickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var subscriberViewModel: SubscriberViewModel
    private lateinit var customAdapter: CustomAdapter
//    private lateinit var adapter: CustomAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_main)
        setContentView(binding.root)

        val dao=SubscriberDatabase.getInstance(application).subscriberDAO
        val repository=SubscriberRepository(dao)
        val factory=ViewModelProviderFactory(repository)
        subscriberViewModel= ViewModelProvider(this,factory).get(SubscriberViewModel::class.java)


        binding.mySubscriberViewModel=subscriberViewModel
        binding.lifecycleOwner=this
        displayDataRecycle()
    }

    private fun displayDataRecycle() {
        binding.recycle.layoutManager=LinearLayoutManager(this)
        subscriberViewModel.susbcriber.observe(this,Observer{
            binding.recycle.adapter=CustomAdapter(it,this)
        })
    }

    override fun onClick(subscriber: Subscriber) {
        subscriberViewModel.updateOrDelete(subscriber)
        Toast.makeText(this,"Clicked "+subscriber.name,Toast.LENGTH_SHORT).show()
    }


}