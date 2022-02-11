package com.example.cininfo.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cininfo.R
import com.example.cininfo.databinding.FragmentSearchResultBinding
import com.example.cininfo.data.FilmDTO
import com.example.cininfo.logic.AppState
import com.example.cininfo.logic.FoundFilmRecyclerAdapter
import com.example.cininfo.logic.MainViewModel
import com.google.android.material.snackbar.Snackbar

class SearchResultFragment : Fragment() {

    private var _binding: FragmentSearchResultBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        fun newInstance(bundle: Bundle): SearchResultFragment {
            val fragment = SearchResultFragment()
            fragment.arguments = bundle

            Log.i("FOUNDED_LIST", "${fragment.arguments?.getString(BUNDLE_TEXT)} ${fragment.arguments?.getBoolean(
                BUNDLE_IS_ADULT)} in object")
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val searchText = arguments?.getString(BUNDLE_TEXT)
        val isAdult = arguments?.getBoolean(BUNDLE_IS_ADULT)

        binding.searchText.text = searchText

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.getLiveData().observe(viewLifecycleOwner, { renderData(it) })
        viewModel.getFoundFilmDataFromRemoteSource(searchText, isAdult)
    }

    private fun renderData(appState: AppState?) {
        val loadingLayout = binding.loadingLayout

        loadingLayout.apply {
            when (appState) {
                is AppState.Success -> {}
                is AppState.SearchSuccess -> {
                    val foundFilmData = appState.foundFilmData
                    visibility = View.GONE
                    setData(foundFilmData)
                    showSnackBar(R.string.snack_bar_success_text)
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
                else -> {}
            }
        }
    }

    private fun setData(foundFilmData: List<FilmDTO>?) {
        val manager = activity?.supportFragmentManager

        val foundRView = binding.foundFilmsRView
        foundRView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        foundRView.adapter = FoundFilmRecyclerAdapter(
            foundFilmData,
            FoundFilmRecyclerAdapter.OnItemClickListener { filmData ->
                resultClicker(filmData, manager)
            }
        )
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