package com.example.cininfo.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cininfo.data.FilmList
import com.example.cininfo.databinding.MainFragmentBinding
import com.example.cininfo.logic.AppState
import com.example.cininfo.logic.FilmItemRecyclerAdapter
import com.example.cininfo.logic.MainViewModel
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment() {

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer { renderData(it) })
        viewModel.getFilmList()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun renderData(appState: AppState) {

        val loadingLayout = binding.loadingLayout
        val mainView = binding.mainView

        when (appState) {
            is AppState.Success -> {
                val filmData = appState.filmData
                loadingLayout.visibility = View.GONE
                setData(filmData)
                Snackbar.make(mainView, "Success", Snackbar.LENGTH_LONG).show()
            }
            is AppState.Loading -> {
                loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                loadingLayout.visibility = View.GONE
                Snackbar
                    .make(mainView, "Error", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Reload") { viewModel.getFilmList() }
                    .show()
            }
        }
    }

    private fun setData(filmData: FilmList) {
        val freshRView = binding.freshRView
        val popularRView = binding.popularRView

        freshRView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        freshRView.adapter = FilmItemRecyclerAdapter(filmData.getFreshFilms())

        popularRView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        popularRView.adapter = FilmItemRecyclerAdapter(filmData.getPopularFilms())
    }
}