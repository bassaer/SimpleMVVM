package com.github.bassaer.simplemvvm.userlist

import androidx.databinding.BaseObservable
import androidx.databinding.ObservableField
import com.github.bassaer.simplemvvm.data.local.User
import com.github.bassaer.simplemvvm.data.local.UserRepository
import java.lang.ref.WeakReference

class UserItemViewModel(userRepository: UserRepository): BaseObservable() {

    private val userObservable = ObservableField<User>()
    val name = ObservableField<String>()
    val count = ObservableField<String>()

    private var navigator: WeakReference<UserItemNavigator>? = null

    fun userClicked() {
        val userId = getUserId() ?: return
        navigator?.get()?.openCounter(userId)
    }

    private fun getUserId() = userObservable.get()?.id
}
