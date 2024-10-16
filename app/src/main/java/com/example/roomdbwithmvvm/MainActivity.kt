package com.example.roomdbwithmvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdbwithmvvm.adapter.CustomAdapter
import com.example.roomdbwithmvvm.adapter.OnCLickListener
import com.example.roomdbwithmvvm.databinding.ActivityMainBinding
import com.example.roomdbwithmvvm.db.Subscriber
import com.example.roomdbwithmvvm.db.SubscriberDatabase
import com.example.roomdbwithmvvm.db.SubscriberRepository
import com.example.roomdbwithmvvm.db.ViewModelProviderFactory

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
    customAdapter= CustomAdapter(this)
    binding.recycle.adapter=customAdapter
        displayDataRecycle()

    subscriberViewModel.message.observe(this,Observer{
        it?.getContentIfNotHandled()?.let {
            Toast.makeText(this,it,Toast.LENGTH_SHORT).show()
        }
    })
    }

    private fun displayDataRecycle() {
        binding.recycle.layoutManager=LinearLayoutManager(this)
        subscriberViewModel.susbcriber.observe(this,Observer{
           customAdapter.setList(it)
        })
    }

    override fun onClick(subscriber: Subscriber) {
        subscriberViewModel.updateOrDelete(subscriber)
        Toast.makeText(this,"Clicked "+subscriber.name,Toast.LENGTH_SHORT).show()
    }


}