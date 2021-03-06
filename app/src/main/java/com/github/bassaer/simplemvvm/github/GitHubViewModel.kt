package com.github.bassaer.simplemvvm.github

import android.util.Log
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableArrayList
import com.github.bassaer.simplemvvm.BR
import com.github.bassaer.simplemvvm.data.source.remote.RemoteRepository
import com.github.bassaer.simplemvvm.data.source.remote.RepoInfo
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class GitHubViewModel(private val remoteRepository: RemoteRepository) : BaseObservable() {
    var repoList = ObservableArrayList<RepoInfo>()

    fun readRepoList() {
        Log.d(javaClass.simpleName, "readRepoList!!")
        remoteRepository.getRepoList()
            .readRepos()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<List<RepoInfo>> {
                override fun onComplete() {}

                override fun onSubscribe(disposable: Disposable) {}

                override fun onNext(repos: List<RepoInfo>) {
                    repoList.clear()
                    repoList.addAll(repos)
                    notifyPropertyChanged(BR.empty)
                }

                override fun onError(e: Throwable) {}
            })
    }

    @Bindable
    fun isEmpty() = repoList.isEmpty()

}