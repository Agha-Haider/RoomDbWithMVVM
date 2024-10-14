package com.example.roomdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.roomdemo.databinding.ActivityMainBinding
import com.example.roomdemo.db.SubscriberDatabase
import com.example.roomdemo.db.SubscriberRepository
import com.example.roomdemo.db.ViewModelProviderFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var subscriberViewModel: SubscriberViewModel
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
        binding.recycle
    }


}