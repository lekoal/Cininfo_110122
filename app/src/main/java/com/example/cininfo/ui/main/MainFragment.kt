package com.example.cininfo.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
        viewModel.getFilmDataFromLocalSource()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun renderData(appState: AppState) {

        val loadingLayout = binding.loadingLayout
        val mainView = binding.mainView

        var appStateChanged = appState

        if ((1..10).random() > 4) appStateChanged = AppState.Error(Throwable("Error"))

        when (appStateChanged) {
            is AppState.Success -> {
                val filmData = appStateChanged.filmData
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
                    .setAction("Reload") {
                        viewModel.getFilmDataFromLocalSource()
                        renderData(appState)
                    }
                    .show()
            }
        }
    }

    private fun setData(filmData: FilmList) {
        val freshRView = binding.freshRView
        val popularRView = binding.popularRView

        freshRView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        freshRView.adapter = FilmItemRecyclerAdapter(
            filmData.getFreshFilms(),
            FilmItemRecyclerAdapter.OnItemClickListener { filmData ->
                Toast.makeText(requireContext(), "${filmData.name} is pressed", Toast.LENGTH_SHORT)
                    .show()
            })


        popularRView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        popularRView.adapter = FilmItemRecyclerAdapter(
            filmData.getPopularFilms(),
            FilmItemRecyclerAdapter.OnItemClickListener { filmData ->
                Toast.makeText(requireContext(), "${filmData.name} is pressed", Toast.LENGTH_SHORT)
                    .show()
            })
    }
}