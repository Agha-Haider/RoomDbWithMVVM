package com.example.roomdbwithmvvm.db

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.roomdbwithmvvm.SubscriberViewModel

class ViewModelProviderFactory(private val repository: SubscriberRepository):ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

      if (modelClass.isAssignableFrom(SubscriberViewModel::class.java)){
          return SubscriberViewModel(repository) as T
      }
        throw  IllegalArgumentException("unknown ViewModel Class")

    }
}