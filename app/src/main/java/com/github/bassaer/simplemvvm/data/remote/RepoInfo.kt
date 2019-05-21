package com.github.bassaer.simplemvvm.data.remote

import java.io.Serializable

data class RepoInfo (
    var name: String,
    var language: String,
    var star: Int
): Serializable
