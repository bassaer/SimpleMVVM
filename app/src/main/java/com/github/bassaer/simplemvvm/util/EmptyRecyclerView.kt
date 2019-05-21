package com.github.bassaer.simplemvvm.util

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class EmptyRecyclerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
): RecyclerView(context, attrs, defStyleAttr) {

    private lateinit var emptyView: View

    private fun initView() {
        emptyView.let {
            if (adapter == null || adapter?.itemCount == 0) {
                it.visibility = View.VISIBLE
            } else {
                it.visibility = View.GONE
            }
        }
    }

    override fun setAdapter(adapter: Adapter<*>?) {
        val oldAdapter = getAdapter()
        super.setAdapter(adapter)
        oldAdapter?.unregisterAdapterDataObserver(emptyObserver)
        adapter?.registerAdapterDataObserver(emptyObserver)
    }

    private val emptyObserver = object : RecyclerView.AdapterDataObserver() {
        override fun onChanged() {
            super.onChanged()
            initView()
        }
    }

    fun setEmptyView(view: View) {
        emptyView = view
        initView()
    }
}