package com.example.cininfo.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cininfo.R
import com.example.cininfo.data.FilmDTO
import com.example.cininfo.data.FilmData
import com.example.cininfo.data.FilmList
import com.example.cininfo.databinding.FragmentFilmDetailBinding

class FilmDetailFragment : Fragment() {

    private var _binding: FragmentFilmDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilmDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getParcelable<FilmDTO>(BUNDLE_EXTRA)?.let {
            with(binding) {
                it.apply {
                    filmNameRus.text = title
                    filmNameOriginal.text = original_title
                    filmReleaseYear.text = release_date
                    detailsFilmImage.setImageResource(R.drawable.big_no_image_temp)
                    detailsLongDescription.text = overview
                }
            }
        }
    }

    companion object {

        const val BUNDLE_EXTRA = "filmData"

        fun newInstance(bundle: Bundle): FilmDetailFragment {
            val fragment = FilmDetailFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}