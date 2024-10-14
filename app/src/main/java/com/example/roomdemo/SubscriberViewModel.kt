package com.example.roomdemo

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomdemo.db.Subscriber
import com.example.roomdemo.db.SubscriberRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SubscriberViewModel(private  val repository: SubscriberRepository):ViewModel() {

    val inputName=MutableLiveData<String>()
    val inputEmail=MutableLiveData<String>()

    val saveOrUpdate=MutableLiveData<String>()
    val deleteOrClear=MutableLiveData<String>()

    val susbcriber=repository.dao

    init {
        saveOrUpdate.value="Save"
        deleteOrClear.value="Clear All"
    }
    fun saveOrUpdate() {
        val name = inputName.value
        val email = inputEmail.value
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email)) {
            val subscriber = Subscriber(0, name!!, email!!)
            insertToSubscriber(subscriber)
        }
}
    suspend fun delete(subscriber: Subscriber){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deletesubscriber(subscriber)
        }
    }
    private fun insertToSubscriber(subscriber: Subscriber) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertSubscriber(subscriber)
        }
    }
}