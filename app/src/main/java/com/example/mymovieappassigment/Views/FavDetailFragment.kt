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
import com.example.mymovieappassigment.databinding.FragmentFavDetailBinding
import com.example.mymovieappassigment.viewModel.FavDetailFragmentViewModel
import com.example.mymovieappassigment.viewModel.FavDetailFragmentViewModelFactory

class FavDetailFragment : Fragment() {

    private lateinit var binding: FragmentFavDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_fav_detail,
            container,
            false
        )

        val detailFavFragmentArgs by navArgs<com.example.mymovieappassigment.Views.FavDetailFragmentArgs>()

        val viewModel: FavDetailFragmentViewModel by lazy {
            val viewModelFactory = FavDetailFragmentViewModelFactory(detailFavFragmentArgs.favModel)
            ViewModelProvider(this, viewModelFactory).get(FavDetailFragmentViewModel::class.java)
        }

        binding.detailFavViewModel = viewModel

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