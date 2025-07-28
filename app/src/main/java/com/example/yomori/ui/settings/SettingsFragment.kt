package com.example.yomori.ui.settings

import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.yomori.R
import com.example.yomori.utils.Prefs

class SettingsFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        val switchNotif = view.findViewById<Switch>(R.id.switch_notif)
        val spinnerFreq = view.findViewById<Spinner>(R.id.spinner_freq)

        // Initialiser l'état
        switchNotif.isChecked = Prefs.isNotifEnabled(requireContext())
        val freq = Prefs.getNotifFreqHours(requireContext())
        val freqOptions = resources.getStringArray(R.array.frequency_options)
        spinnerFreq.setSelection(freqOptions.indexOf(freq.toString()))

        // Listeners
        switchNotif.setOnCheckedChangeListener { _, isChecked ->
            Prefs.setNotifEnabled(requireContext(), isChecked)
            scheduleOrCancelWorker()
        }
        spinnerFreq.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val hours = freqOptions[position].toInt()
                Prefs.setNotifFreqHours(requireContext(), hours)
                scheduleOrCancelWorker()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
        return view
    }

    private fun scheduleOrCancelWorker() {
        val context = requireContext().applicationContext
        val enabled = Prefs.isNotifEnabled(context)
        val hours = Prefs.getNotifFreqHours(context)
        val workManager = androidx.work.WorkManager.getInstance(context)
        if (enabled) {
            val request = androidx.work.PeriodicWorkRequestBuilder<com.example.yomori.worker.NewChapterCheckWorker>(
                hours.toLong(), java.util.concurrent.TimeUnit.HOURS
            ).build()
            workManager.enqueueUniquePeriodicWork(
                "new_chapter_check",
                androidx.work.ExistingPeriodicWorkPolicy.REPLACE,
                request
            )
        } else {
            workManager.cancelUniqueWork("new_chapter_check")
        }
    }
} 