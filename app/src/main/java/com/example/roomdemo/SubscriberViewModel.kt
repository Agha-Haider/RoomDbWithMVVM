package com.example.roomdemo

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomdemo.db.Subscriber
import com.example.roomdemo.db.SubscriberRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SubscriberViewModel(private val repository: SubscriberRepository) : ViewModel() {

    val inputName = MutableLiveData<String>()
    val inputEmail = MutableLiveData<String>()

    val saveOrUpdate = MutableLiveData<String>()
    val deleteOrClear = MutableLiveData<String>()
    private lateinit var subscriberUpdateOrDelete: Subscriber

    private var updateorDelete = false

    val susbcriber = repository.dao

    init {
        saveOrUpdate.value = "Save"
        deleteOrClear.value = "Clear All"
    }

    fun saveOrUpdate() {

        if (updateorDelete){
          viewModelScope.launch(Dispatchers.IO) {
                subscriberUpdateOrDelete.name=inputName.value.toString()
                subscriberUpdateOrDelete.email=inputEmail.value.toString()
                    updateToSubscriber(subscriberUpdateOrDelete)
              viewModelScope.launch(Dispatchers.Main){
                  inputName.value=""
                  inputEmail.value=""
                  saveOrUpdate.value="Save"
                  deleteOrClear.value="Clear"
                  updateorDelete = false
              }

            }
        }
        else{
            val name = inputName.value
            val email = inputEmail.value
            viewModelScope.launch {
                Dispatchers.IO
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email)) {
                    val subscriber = Subscriber(0, name!!, email!!)
                    insertToSubscriber(subscriber)
                }
            }
        }
    }

    fun clearAllData() {

        if (updateorDelete){

            viewModelScope.launch(Dispatchers.IO) {
                repository.deletesubscriber(subscriberUpdateOrDelete)
            }
            viewModelScope.launch(Dispatchers.Main) {
                inputName.value=""
                inputEmail.value=""
                saveOrUpdate.value="Save"
                deleteOrClear.value="Clear"
                updateorDelete = false

            }
        }
        else{
            viewModelScope.launch {
                Dispatchers.IO
                repository.deleteAllSubscriber()
            }
        }

    }

    suspend fun delete(subscriber: Subscriber) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deletesubscriber(subscriber)
        }
    }

    private fun insertToSubscriber(subscriber: Subscriber) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertSubscriber(subscriber)
        }
    }

    suspend fun updateToSubscriber(subscriber: Subscriber){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateSubscriber(subscriber)
        }
    }
    fun updateOrDelete(subscriber: Subscriber) {

        inputName.value = subscriber.name
        inputEmail.value = subscriber.email

        saveOrUpdate.value = "update"
        deleteOrClear.value = "Delete"
        updateorDelete = true
        subscriberUpdateOrDelete=subscriber


    }
}