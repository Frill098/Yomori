package com.example.yomori.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yomori.data.remote.NovelsFireSource
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {
    private val viewModel: SearchViewModel by viewModels()
    private lateinit var adapter: SearchResultsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        val searchInput = view.findViewById<EditText>(R.id.search_input)
        val searchButton = view.findViewById<Button>(R.id.search_button)
        val recyclerView = view.findViewById<RecyclerView>(R.id.search_results)

        adapter = SearchResultsAdapter()
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        searchButton.setOnClickListener {
            val query = searchInput.text.toString()
            if (query.isNotBlank()) viewModel.search(query)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.results.collectLatest { results ->
                adapter.submitList(results)
            }
        }

        return view
    }
} 