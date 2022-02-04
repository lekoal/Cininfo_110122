package com.example.cininfo.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import coil.load
import coil.size.Precision
import coil.size.Scale
import com.example.cininfo.R
import com.example.cininfo.app.App.Companion.getFilmNoteDao
import com.example.cininfo.data.*
import com.example.cininfo.databinding.FragmentFilmDetailBinding

class FilmDetailFragment : Fragment() {

    private val imageBaseUrl = "https://www.themoviedb.org/t/p/original"
    private var _binding: FragmentFilmDetailBinding? = null
    private val binding get() = _binding!!

    private val notesRepository: LocalNotesRepository = LocalNotesRepositoryImpl(getFilmNoteDao())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilmDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var filmId: Int? = null

        arguments?.getParcelable<FilmDTO>(BUNDLE_EXTRA)?.let {
            with(binding) {
                it.apply {
                    filmId = id
                    filmNameRus.text = title
                    filmNameOriginal.text = original_title
                    filmReleaseYear.text = release_date
                    detailsFilmImage.load("$imageBaseUrl$poster_path") {
                        precision(Precision.EXACT)
                        error(R.drawable.big_no_image_temp)
                        scale(Scale.FILL)
                    }
                    detailsLongDescription.text = overview
                }
            }
        }

        if (filmId != null) {
            val noteText = notesRepository.getFilmNote(filmId)
            if (noteText.isNullOrEmpty()) {
                binding.filmNoteEditLayout.visibility = View.VISIBLE
                binding.filmNoteTextLayout.visibility = View.GONE
            } else {
                binding.filmNoteEditLayout.visibility = View.GONE
                binding.filmNoteTextLayout.visibility = View.VISIBLE
                binding.noteText.text = noteText
            }
        }

        binding.saveNoteButton.setOnClickListener {
            if (!binding.noteEdit.text.isNullOrEmpty()) {
                val noteEditText = binding.noteEdit.text.toString()
                saveNoteToDB(filmId, noteEditText)
                binding.noteEdit.isEnabled = false
                binding.saveNoteButton.isEnabled = false
            } else {
                Toast.makeText(requireContext(),R.string.nothing_to_save, Toast.LENGTH_SHORT).show()
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

    private fun saveNoteToDB(id: Int?, noteText: String?) {
        Thread(kotlinx.coroutines.Runnable {
            notesRepository.saveNoteEntity(id, noteText)
        }).start()
    }
}