package com.cyclone.simbirsoftprobation.domain.repository.event

import com.cyclone.simbirsoftprobation.domain.model.Event
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single

interface EventsRepository {

    fun getEvent(id: String): Single<Event>

    fun getEvents(): Flowable<List<Event>>

    fun insertEvent(event: Event)

    fun insertEvents(events: List<Event>)

    fun updateEvent(event: Event)

    fun deleteEvent(event: Event)

    fun deleteEventById(id: String)

    fun deleteAll()
}