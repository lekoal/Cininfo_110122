package com.example.cininfo.ui.main

import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.BuildConfig
import com.example.cininfo.R
import com.example.cininfo.data.FilmDTO
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

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.getLiveData().observe(viewLifecycleOwner, { renderData(it) })
        viewModel.getFilmDataFromRemoteSource()

//        binding.buildVersion.text = getString(R.string.build_version, com.example.cininfo.BuildConfig.TYPE)
        binding.buildVersion.text = getString(R.string.hello)


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun renderData(appState: AppState) {

        val loadingLayout = binding.loadingLayout

        loadingLayout.apply {
            when (appState) {
                is AppState.Success -> {
                    val freshFilmData = appState.freshFilmData
                    visibility = View.GONE
                    setData(freshFilmData)
                    showSnackBar(R.string.snack_bar_success_text)
                }
                is AppState.SearchSuccess -> {

                }
                is AppState.Loading -> {
                    visibility = View.VISIBLE
                }
                is AppState.Error -> {
                    visibility = View.GONE
                    showActionSnackBar(
                        R.string.snack_bar_error_text,
                        R.string.snack_bar_action_text,
                        appState
                    )

                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun setData(freshFilmList: List<FilmDTO>?) {

        val manager = activity?.supportFragmentManager

        val freshRView = binding.freshRView

        freshRView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        freshRView.adapter = FilmItemRecyclerAdapter(
            freshFilmList,
            FilmItemRecyclerAdapter.OnItemClickListener { filmData ->
                resultClicker(filmData, manager)
            })
    }

    private fun resultClicker(filmData: FilmDTO?, manager: FragmentManager?) {
        if (manager != null) {
            val bundle = Bundle()
            bundle.putParcelable(FilmDetailFragment.BUNDLE_EXTRA, filmData)
            manager.beginTransaction()
                .add(R.id.container, FilmDetailFragment.newInstance(bundle))
                .addToBackStack("")
                .commitAllowingStateLoss()
        }
    }

    private fun View.showSnackBar(
        text: Int,
        length: Int = Snackbar.LENGTH_SHORT
    ) {
        Snackbar.make(this, text, length).show()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun View.showActionSnackBar(
        text: Int,
        actionText: Int,
        appState: AppState,
        length: Int = Snackbar.LENGTH_INDEFINITE
    ) {
        Snackbar.make(this, text, length).setAction(actionText) {
            viewModel.getFilmDataFromRemoteSource()
            renderData(appState)
        }.show()
    }
}