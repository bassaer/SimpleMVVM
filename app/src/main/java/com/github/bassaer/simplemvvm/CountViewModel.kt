package com.github.bassaer.simplemvvm

import android.databinding.BaseObservable
import android.databinding.Observable
import android.databinding.ObservableField

class CountViewModel: BaseObservable() {
    val count = ObservableField<Int>()
    val statusObservable = ObservableField<Status>()
    var countNavigator: CountNavigator? = null

    init {
        statusObservable.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                val status: Status? = statusObservable.get()
                count.set(status?.count)
            }
        })
    }

    fun setNavivator(navigator: CountNavigator) {
        countNavigator = navigator
    }

    fun onActivityDestroyed() {
        countNavigator = null
    }

    fun countUp() {
        countNavigator?.countUp()
    }


    fun setStatus(status: Status) {
        statusObservable.set(status)
    }

}