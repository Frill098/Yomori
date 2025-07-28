package com.example.yomori.ui.reader

import android.os.Bundle
import android.view.*
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yomori.data.remote.NovelsFireSource
import kotlinx.coroutines.launch

class ChaptersFragment : Fragment() {
    private lateinit var adapter: ChaptersAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_chapters, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.chapters_list)
        val progressBar = view.findViewById<ProgressBar>(R.id.progress_bar)
        adapter = ChaptersAdapter { chapter ->
            // Naviguer vers le ReaderFragment
            val action = ChaptersFragmentDirections.actionChaptersToReader(chapter.url, chapter.title)
            findNavController().navigate(action)
        }
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        val novelUrl = arguments?.getString("novelUrl") ?: return view
        progressBar.visibility = View.VISIBLE
        viewLifecycleOwner.lifecycleScope.launch {
            val chapters = NovelsFireSource.getChapters(novelUrl)
            adapter.submitList(chapters)
            progressBar.visibility = View.GONE
        }
        return view
    }
} 