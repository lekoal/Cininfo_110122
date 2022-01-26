package com.example.cininfo.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cininfo.data.FilmData
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

        arguments?.getParcelable<FilmData>(BUNDLE_EXTRA)?.let {
            with(binding) {
                it.apply {
                    filmNameRus.text = name
                    filmNameOriginal.text = originalName
                    filmReleaseYear.text = releaseDate
                    detailsFilmImage.setImageResource(bigImage)
                    detailsShortDescription.text = shortDescription
                    detailsLongDescription.text = longDescription
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