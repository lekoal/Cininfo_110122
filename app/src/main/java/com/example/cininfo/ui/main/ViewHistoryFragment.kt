package com.example.cininfo.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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
            setOnLongClickListener {
                showAlertDialog(this)
                true
            }
        })
    }

    private fun showAlertDialog(view: View) {
        val builder = AlertDialog.Builder(view.context)
        val inflater = layoutInflater
        builder.setTitle(R.string.title_filtration_text)
        val dialogLayout = inflater.inflate(R.layout.alert_dialog_filter, null)
        val editText = dialogLayout.findViewById<EditText>(R.id.filter_text)
        builder.setView(dialogLayout)
        builder.setPositiveButton(R.string.filter_ok_button) { _, _ ->
            if (editText.text.isNullOrEmpty()) Toast.makeText(
                view.context,
                R.string.empty_filter_edit_text_error,
                Toast.LENGTH_SHORT
            ).show()
            else {
                binding.viewHistoryLayout.removeAllViews()
                val filterTitle = editText.text.toString()
                binding.viewHistoryLayout.addView(TextView(view.context).apply {
                    text = getString(R.string.filter_title_result, filterTitle)
                    textSize = resources.getDimension(R.dimen.contacts_name_size)
                })
                watchesRepository.getFilteredWatchInfoByTitle(filterTitle).let { cursor ->
                    for (i in 0 until cursor.count()) {
                        val title = cursor[i].title
                        val date = cursor[i].date
                        val time = cursor[i].watchTime
                        val note = cursor[i].note
                        addView(title, date, time, note)
                    }
                }
            }
        }
        builder.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}