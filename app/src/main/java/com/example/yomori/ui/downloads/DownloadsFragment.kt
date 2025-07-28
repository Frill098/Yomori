package com.example.yomori.ui.downloads

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yomori.R
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DownloadsFragment : Fragment() {
    private val viewModel: DownloadsViewModel by viewModels()
    private lateinit var adapter: DownloadsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_downloads, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.downloads_recycler)
        adapter = DownloadsAdapter(
            onRetry = { item -> viewModel.retryDownload(item.workId) },
            onDelete = { item -> viewModel.deleteDownload(item.workId) }
        )
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.downloads.collectLatest { downloads ->
                adapter.submitList(downloads)
            }
        }
        return view
    }
} 