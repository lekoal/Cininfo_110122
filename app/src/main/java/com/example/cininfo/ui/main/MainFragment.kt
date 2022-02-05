package com.example.cininfo.ui.main

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentManager
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cininfo.BuildConfig
import com.example.cininfo.R
import com.example.cininfo.data.FilmDTO
import com.example.cininfo.data.FilmOuterDTO
import com.example.cininfo.data.ResultURL
import com.example.cininfo.databinding.MainFragmentBinding
import com.example.cininfo.logic.*
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

const val FRESH_FILM_LIST = "FRESH_FILM_LIST"
const val POPULAR_FILM_LIST = "POPULAR_FILM_LIST"
const val DETAILS_INTENT_FILTER = "com.example.cininfo.receive_film_data"

class MainFragment : Fragment() {
    //    ------------------------------------------------------
    private var freshFilmList: List<FilmDTO>? = null
    private var popularFilmList: List<FilmDTO>? = null

    private val filmDataIntentReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        @RequiresApi(Build.VERSION_CODES.N)
        override fun onReceive(context: Context?, intent: Intent?) {
            val tempFreshList: List<FilmDTO>? = intent?.getParcelableArrayListExtra(FRESH_FILM_LIST)

            val tempPopularList: List<FilmDTO>? = intent?.getParcelableArrayListExtra(POPULAR_FILM_LIST)

            setData(tempFreshList, tempPopularList)
        }
    }
//    ------------------------------------------------------

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel

    companion object {
        fun newInstance() = MainFragment()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        getFilmsServer()
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context?.registerReceiver(filmDataIntentReceiver, IntentFilter(DETAILS_INTENT_FILTER))
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
//        viewModel.getLiveData().observe(viewLifecycleOwner) { renderData(it) }
//        viewModel.getFilmDataFromRemoteSource()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        context?.unregisterReceiver(filmDataIntentReceiver)
        super.onDestroy()
    }

//    @RequiresApi(Build.VERSION_CODES.N)
//    private fun renderData(appState: AppState) {
//
//        val loadingLayout = binding.loadingLayout
//
////        FilmDataReceiver.getFilmsServer()
//
//        getFilmsServer()
//
//        loadingLayout.apply {
//            when (appState) {
//                is AppState.Success -> {
////                    val freshFilmData = appState.freshFilmData
////                    val popularFilmData = appState.popularFilmData
//                    visibility = View.GONE
//                    setData(freshFilmList, popularFilmList)
//                    Log.i("STATUS", freshFilmList.toString())
//                    showSnackBar(R.string.snack_bar_success_text)
//                }
//                is AppState.Loading -> {
//                    visibility = View.VISIBLE
//                }
//                is AppState.Error -> {
//                    visibility = View.GONE
//                    showActionSnackBar(
//                        R.string.snack_bar_error_text,
//                        R.string.snack_bar_action_text,
//                        appState
//                    )
//                }
//            }
//        }
//    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun setData(freshFilmList: List<FilmDTO>?, popularFilmList: List<FilmDTO>?) {

        val manager = activity?.supportFragmentManager

        val freshRView = binding.freshRView
        val popularRView = binding.popularRView

        freshRView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        freshRView.adapter = FilmItemRecyclerAdapter(
            freshFilmList,
            FilmItemRecyclerAdapter.OnItemClickListener { filmData ->
                resultClicker(filmData, manager)
            })

        popularRView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        popularRView.adapter = FilmItemRecyclerAdapter(
            popularFilmList,
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
//            renderData(appState)
        }.show()
    }

    //    ------------------------------------------------------------------
    @RequiresApi(Build.VERSION_CODES.N)
    fun getFilmsServer() {
        context.let {
            it?.startService(Intent(it, GetDataService::class.java))
        }
    }
}