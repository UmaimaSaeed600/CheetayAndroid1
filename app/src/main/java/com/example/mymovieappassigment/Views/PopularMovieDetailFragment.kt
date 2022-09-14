package com.example.mymovieappassigment.Views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.mymovieappassigment.R
import com.example.mymovieappassigment.databinding.FragmentDetailBinding
import com.example.mymovieappassigment.viewModel.DetailFragmentViewModel
import com.example.mymovieappassigment.viewModel.DetailFragmentViewModelFactory

class PopularMovieDetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_detail,
            container,
            false
        )

        val detailFragmentArgs by navArgs<com.example.mymovieappassigment.Views.PopularMovieDetailFragmentArgs>()

        val viewModel: DetailFragmentViewModel by lazy {
            val viewModelFactory = DetailFragmentViewModelFactory(detailFragmentArgs.movies)
            ViewModelProvider(this, viewModelFactory).get(DetailFragmentViewModel::class.java)
        }

        binding.detailViewModel = viewModel

        viewModel.movies.observe(viewLifecycleOwner, Observer { movies ->
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w92${movies.backdropPath}")
                .transform(CenterCrop())
                .into(binding.movieBackdrop)

            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w92${movies.posterPath}")
                .transform(CenterCrop())
                .into(binding.moviePoster)
        })

        return binding.root
    }
}