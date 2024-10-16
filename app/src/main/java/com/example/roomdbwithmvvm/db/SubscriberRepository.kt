package com.example.roomdbwithmvvm.db

class SubscriberRepository(private  val subscriberDAO: SubscriberDAO) {

    val dao=subscriberDAO.getAllSubscribers()

     suspend fun insertSubscriber(subscriber: Subscriber){
        subscriberDAO.insertSubscriber(subscriber)
    }

    suspend fun updateSubscriber(subscriber: Subscriber){
        subscriberDAO.updateSubscriber(subscriber)
    }

    suspend fun deletesubscriber(subscriber: Subscriber){
        subscriberDAO.deleteSubscriber(subscriber)
    }

    suspend fun deleteAllSubscriber(){
        subscriberDAO.deleteAll()
    }
}