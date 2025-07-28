package com.example.yomori.ui.reader

import android.os.Bundle
import android.view.*
import android.webkit.WebView
import android.widget.ImageButton
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.yomori.R
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ReaderFragment : Fragment() {
    private val viewModel: ReaderViewModel by viewModels()
    private lateinit var webView: WebView
    private var chapterHtml: String = ""
    private var chapterUrl: String = ""
    private var novelId: String = ""
    private var chapterId: String = ""
    private var chapterTitle: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        arguments?.let {
            chapterUrl = it.getString("chapterUrl") ?: ""
            novelId = it.getString("novelId") ?: ""
            chapterId = it.getString("chapterId") ?: ""
            chapterTitle = it.getString("chapterTitle") ?: ""
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_reader, container, false)
        webView = view.findViewById(R.id.chapter_webview)
        val fontPlus = view.findViewById<ImageButton>(R.id.font_plus)
        val fontMinus = view.findViewById<ImageButton>(R.id.font_minus)
        val themeBtn = view.findViewById<ImageButton>(R.id.theme_button)

        fontPlus.setOnClickListener { viewModel.increaseFont() }
        fontMinus.setOnClickListener { viewModel.decreaseFont() }
        themeBtn.setOnClickListener { showThemeMenu(themeBtn) }

        // Charger le chapitre
        viewLifecycleOwner.lifecycleScope.launch {
            chapterHtml = viewModel.getChapterContent(chapterUrl)
            applyThemeAndFont()
            // Marque-page automatique
            viewModel.setBookmark(novelId, chapterId, chapterTitle)
        }

        // Observer les changements de thème et de taille
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.theme.collectLatest { applyThemeAndFont() }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.fontSize.collectLatest { applyThemeAndFont() }
        }

        return view
    }

    private fun applyThemeAndFont() {
        val theme = viewModel.theme.value
        val fontSize = viewModel.fontSize.value
        val css = """
            <style>
                body { margin: 0; padding: 8px; font-size: ${fontSize}px; line-height: 1.6; ${getThemeCss(theme)} }
            </style>
        """.trimIndent()
        webView.loadDataWithBaseURL(null, "$css$chapterHtml", "text/html", "utf-8", null)
    }

    private fun getThemeCss(theme: ReaderTheme): String = when (theme) {
        ReaderTheme.LIGHT -> "background: #fff; color: #222;"
        ReaderTheme.DARK -> "background: #181818; color: #eee;"
        ReaderTheme.SEPIA -> "background: #f4ecd8; color: #5b4636;"
    }

    private fun showThemeMenu(anchor: View) {
        val popup = PopupMenu(requireContext(), anchor)
        popup.menu.add(0, 0, 0, "Clair")
        popup.menu.add(0, 1, 1, "Sombre")
        popup.menu.add(0, 2, 2, "Sépia")
        popup.setOnMenuItemClickListener {
            when (it.itemId) {
                0 -> viewModel.setTheme(ReaderTheme.LIGHT)
                1 -> viewModel.setTheme(ReaderTheme.DARK)
                2 -> viewModel.setTheme(ReaderTheme.SEPIA)
            }
            true
        }
        popup.show()
    }
} 