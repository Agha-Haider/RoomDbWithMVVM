package com.example.roomdbwithmvvm

import android.text.TextUtils
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomdbwithmvvm.db.Subscriber
import com.example.roomdbwithmvvm.db.SubscriberRepository
import com.example.roomdbwithmvvm.event.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SubscriberViewModel(private val repository: SubscriberRepository) : ViewModel() {

    val inputName = MutableLiveData<String>()
    val inputEmail = MutableLiveData<String>()

    val saveOrUpdate = MutableLiveData<String>()
    val deleteOrClear = MutableLiveData<String>()
    private lateinit var subscriberUpdateOrDelete: Subscriber
    var statusMessage = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>> get() = statusMessage

    private var updateorDelete = false

    val susbcriber = repository.dao

    init {
        saveOrUpdate.value = "Save"
        deleteOrClear.value = "Clear All"
    }

    fun saveOrUpdate() {
        if (updateorDelete) {

                viewModelScope.launch(Dispatchers.IO) {
                    subscriberUpdateOrDelete.name = inputName.value.toString()
                    subscriberUpdateOrDelete.email = inputEmail.value.toString()
                    updateToSubscriber(subscriberUpdateOrDelete)
                    viewModelScope.launch(Dispatchers.Main) {
                        inputName.value = ""
                        inputEmail.value = ""
                        saveOrUpdate.value = "Save"
                        deleteOrClear.value = "Clear"
                        updateorDelete = false
                        statusMessage.value = Event("subscribers updated sucessfully")
                    }
                }
        } else {
            val name = inputName.value
            val email = inputEmail.value



            if (inputName.value == null || inputEmail.value == null) {
                statusMessage.value = Event("please insert name and email")
            }

            else if (!Patterns.EMAIL_ADDRESS.matcher(inputEmail.value.toString()).matches()) {
                statusMessage.value = Event("invalid email address")

            }
              else{
                    viewModelScope.launch {
                        Dispatchers.IO
                        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email)) {
                            val subscriber = Subscriber(0, name!!, email!!)
                            insertToSubscriber(subscriber)
                        }
                        withContext(Dispatchers.Main) {
                            statusMessage.value = Event("subscribers inserted sucessfully")
                        }

                    }

            }
        }
    }

    fun clearAllData() {
        if (updateorDelete) {
            viewModelScope.launch(Dispatchers.IO) {
                repository.deletesubscriber(subscriberUpdateOrDelete)
                viewModelScope.launch(Dispatchers.Main) {
                    statusMessage.value = Event(" subscriber deleted sucessfully")
                }
            }
            viewModelScope.launch(Dispatchers.Main) {
                inputName.value = ""
                inputEmail.value = ""
                saveOrUpdate.value = "Save"
                deleteOrClear.value = "Clear"
                updateorDelete = false

            }
        } else {
            viewModelScope.launch {
                Dispatchers.IO
                repository.deleteAllSubscriber()
            }
            viewModelScope.launch(Dispatchers.Main) {
                statusMessage.value = Event("All subscribers deleted sucessfully")
                inputName.value = ""
                inputEmail.value = ""
                saveOrUpdate.value = "Save"
                deleteOrClear.value = "Clear"
                updateorDelete = false
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

    suspend fun updateToSubscriber(subscriber: Subscriber) {
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
        subscriberUpdateOrDelete = subscriber
    }
}