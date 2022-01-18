package com.example.cininfo.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cininfo.R
import com.example.cininfo.data.FilmData
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
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.getLiveData().observe(viewLifecycleOwner, { renderData(it) })
        viewModel.getFilmDataFromLocalSource()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun renderData(appState: AppState) {

        val loadingLayout = binding.loadingLayout
        val mainView = binding.mainView

        loadingLayout.apply {
            when (appState) {
                is AppState.Success -> {
                    val filmData = appState.filmData
                    visibility = View.GONE
                    setData(filmData)
                    Snackbar.make(mainView, "Success", Snackbar.LENGTH_LONG).show()
                }
                is AppState.Loading -> {
                    visibility = View.VISIBLE
                }
                is AppState.Error -> {
                    visibility = View.GONE
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
    }

    private fun setData(filmList: FilmList) {

        val manager = activity?.supportFragmentManager

        val freshRView = binding.freshRView
        val popularRView = binding.popularRView

        freshRView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        freshRView.adapter = FilmItemRecyclerAdapter(
            filmList.getFreshFilms(),
            FilmItemRecyclerAdapter.OnItemClickListener { filmData ->
                resultClicker(filmData, manager)
            })

        popularRView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        popularRView.adapter = FilmItemRecyclerAdapter(
            filmList.getPopularFilms(),
            FilmItemRecyclerAdapter.OnItemClickListener { filmData ->
                resultClicker(filmData, manager)
            })
    }

    private fun resultClicker(filmData: FilmData, manager: FragmentManager?) {
        if (manager != null) {
            val bundle = Bundle()
            bundle.putParcelable(FilmDetailFragment.BUNDLE_EXTRA, filmData)
            manager.beginTransaction()
                .add(R.id.container, FilmDetailFragment.newInstance(bundle))
                .addToBackStack("")
                .commitAllowingStateLoss()
        }
    }
}