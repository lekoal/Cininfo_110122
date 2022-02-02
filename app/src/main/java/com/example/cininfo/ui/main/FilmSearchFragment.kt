package com.example.cininfo.ui.main

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cininfo.R
import com.example.cininfo.app.App.Companion.getSearchHistoryDao
import com.example.cininfo.data.LocalRepository
import com.example.cininfo.data.LocalRepositoryImpl
import com.example.cininfo.databinding.FragmentFilmSearchBinding
import com.example.cininfo.logic.PreviousSearchesRecyclerAdapter

private const val ADULT_SET = "ADULT_SET"

class FilmSearchFragment : Fragment() {

    private var _binding: FragmentFilmSearchBinding? = null
    private val binding get() = _binding!!

    private val searchHistoryRepository: LocalRepository =
        LocalRepositoryImpl(getSearchHistoryDao())

    companion object {
        fun newInstance() = FilmSearchFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilmSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        val editor = sharedPref?.edit()
        binding.adultCheckbox.setOnCheckedChangeListener { _, b ->
            editor?.putBoolean(ADULT_SET, b)
            editor?.apply()
        }
        binding.adultCheckbox.isChecked =
            activity?.getPreferences(Context.MODE_PRIVATE)?.getBoolean(ADULT_SET, false) == true

        loadPreviousSearches(getSearchesFromDB())

        binding.searchButton.setOnClickListener {
            if (binding.searchEditText.text.isEmpty()) {
                Toast.makeText(context, R.string.empty_edit_text_error, Toast.LENGTH_SHORT).show()
            } else {
                saveSearchTextToDB(binding.searchEditText.text.toString())
            }
        }
    }

    private fun saveSearchTextToDB(searchText: String) {
        val thread = Thread(kotlinx.coroutines.Runnable {
            searchHistoryRepository.saveEntity(searchText)
        })
        thread.start()
        while (thread.isAlive) {
            Thread.sleep(10)
        }
        loadPreviousSearches(getSearchesFromDB())
    }

    private fun getSearchesFromDB() : List<String>{
        return searchHistoryRepository.getAllSearchHistory()
    }

    private fun loadPreviousSearches(searchesList: List<String>) {
        val searchesRView = binding.recyclerPreviousSearch

        searchesRView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        searchesRView.adapter = PreviousSearchesRecyclerAdapter(
            searchesList,
            PreviousSearchesRecyclerAdapter.OnItemClickListener { text ->
                resultClicker(text)
            }
        )
    }

    private fun resultClicker(text: String?) {
        binding.searchEditText.setText(text)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}