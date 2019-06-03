package com.github.bassaer.simplemvvm.github

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableArrayList
import com.github.bassaer.simplemvvm.BR
import com.github.bassaer.simplemvvm.data.remote.RemoteRepository
import com.github.bassaer.simplemvvm.data.remote.RepoInfo
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class GitHubViewModel(private val remoteRepository: RemoteRepository) : BaseObservable() {
    var repoList = ObservableArrayList<RepoInfo>()

    fun readRepoList() {
        remoteRepository.getRepoList()
            .readRepos()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<List<RepoInfo>> {
                override fun onComplete() {
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(list: List<RepoInfo>) {
                    repoList.clear()
                    repoList.addAll(list)
                    notifyPropertyChanged(BR.empty)
                }

                override fun onError(e: Throwable) {
                }

            })
    }

    @Bindable
    fun isEmpty() = repoList.isEmpty()

}