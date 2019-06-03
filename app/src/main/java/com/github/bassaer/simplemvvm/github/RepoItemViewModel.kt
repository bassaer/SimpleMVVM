package com.github.bassaer.simplemvvm.github

import androidx.databinding.BaseObservable
import androidx.databinding.ObservableField

class RepoItemViewModel : BaseObservable() {
    var name = ObservableField<String>()
    var language = ObservableField<String>()
    var stars = ObservableField<String>()




}