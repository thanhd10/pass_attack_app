package com.example.pass_attack.utils

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject

/**
 * on adding a new value, only the new value will be emitted
 * all values can be retrieved from the list
 */
class RxObservableWithStoredList<T> {

    private var list: MutableList<T> = mutableListOf()
    private var behaviorSubject: BehaviorSubject<T> = BehaviorSubject.create()

    fun add(value: T) {
        list.add(value)
        behaviorSubject.onNext(value)
    }

    fun getMutableList() = list
    fun getObservable(): Observable<T> = behaviorSubject

}