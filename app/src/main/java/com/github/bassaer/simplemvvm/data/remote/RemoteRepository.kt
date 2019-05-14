package com.github.bassaer.simplemvvm.data.remote

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RemoteRepository(private val service: GitHubService) {

    fun getRepoList(): Observable<List<RepoInfo>> {
        return this.service.readRepos()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
    }
}