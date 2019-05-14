package com.github.bassaer.simplemvvm.userlist

/**
 * Defines the navigation actions that can be called from a list item in the user list.
 */
interface UserItemNavigator {
    fun openCounter(userId: String)
}