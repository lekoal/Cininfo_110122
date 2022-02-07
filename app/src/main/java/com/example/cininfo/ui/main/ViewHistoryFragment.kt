package com.example.cininfo.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.cininfo.R
import com.example.cininfo.app.App
import com.example.cininfo.data.LocalWatchesRepository
import com.example.cininfo.data.LocalWatchesRepositoryImpl
import com.example.cininfo.databinding.FragmentViewHistoryBinding

class ViewHistoryFragment : Fragment() {

    private var _binding: FragmentViewHistoryBinding? = null
    private val binding get() = _binding!!

    private val watchesRepository: LocalWatchesRepository = LocalWatchesRepositoryImpl(
        App.getWatchInfoDao()
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = ViewHistoryFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        watchesRepository.getAllWatchInfo().let { cursor ->
            for (i in 0 until cursor.count()) {
                val title = cursor[i].title
                val date = cursor[i].date
                val time = cursor[i].watchTime
                val note = cursor[i].note

                addView(title, date, time, note)
            }


        }
    }

    private fun addView(title: String?, date: String?, time: Long?, note: String?) = with(binding) {
        viewHistoryLayout.addView(TextView(requireContext()).apply {
            text = getString(R.string.view_history_watches_item, title, date, time, note)
            textSize = resources.getDimension(R.dimen.contacts_name_size)
        })
    }
}