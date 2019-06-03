package com.github.bassaer.simplemvvm.data.remote

import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class RemoteRepository private constructor() {

    fun getRepoList(): GitHubService {
        val moshi = Moshi.Builder().build()
        val okClient = OkHttpClient()
        val retrofit = Retrofit.Builder()
            .client(okClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(API_URL)
            .build()
        return retrofit.create(GitHubService::class.java)
    }

    companion object {
        const val TAG = "GitHubFragment"
        const val API_URL = "https://api.github.com/"
        private var INSTANCE: RemoteRepository? = null

        fun getInstance() : RemoteRepository =
            INSTANCE ?: RemoteRepository().also { INSTANCE = it }
    }

}