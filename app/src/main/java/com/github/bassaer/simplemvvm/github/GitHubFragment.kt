package com.github.bassaer.simplemvvm.github

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.bassaer.simplemvvm.R
import com.github.bassaer.simplemvvm.data.remote.RepoInfo
import com.github.bassaer.simplemvvm.databinding.GithubFragBinding
import com.github.bassaer.simplemvvm.databinding.RepoItemBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class GitHubFragment : Fragment() {

    lateinit var viewModel: GitHubViewModel
    private lateinit var githubFragBinding: GithubFragBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        githubFragBinding = GithubFragBinding.inflate(inflater, container, false)
        githubFragBinding.viewmodel = viewModel
        return githubFragBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupFab()
        setupListAdapter()
    }

    override fun onResume() {
        super.onResume()
        viewModel.readRepoList()
    }

    private fun setupFab() {
        activity?.let {
            it.findViewById<FloatingActionButton>(R.id.reload_fab).apply {
                setOnClickListener {
                    viewModel.readRepoList()
                }
            }

        }
    }

    private fun setupListAdapter() {
        githubFragBinding.repoList.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            this.adapter = GitHubAdapter()
        }
    }

    class GitHubAdapter : RecyclerView.Adapter<GitHubAdapter.GitHubViewHolder>(){

        private var repoList: List<RepoInfo> = ArrayList()

        class GitHubViewHolder(var binding: RepoItemBinding) : RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GitHubViewHolder {
            val binding = RepoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return GitHubViewHolder(binding)
        }

        override fun getItemCount() = repoList.size

        override fun onBindViewHolder(holder: GitHubViewHolder, position: Int) {
            val viewModel = RepoItemViewModel()
            val repo = repoList[position]
            viewModel.name.set(repo.name)
            viewModel.language.set(repo.language)
            viewModel.stars.set(repo.star.toString())
            holder.binding.viewmodel = viewModel
        }

        fun update(repos: List<RepoInfo>) {
            repoList = repos
            notifyDataSetChanged()
        }

        companion object {
            @JvmStatic
            @BindingAdapter("items")
            fun RecyclerView.bindItems(repos: List<RepoInfo>) {
                (adapter as GitHubAdapter).update(repos)
            }
        }

    }

    companion object {
        fun newInstance() = GitHubFragment()
    }
}