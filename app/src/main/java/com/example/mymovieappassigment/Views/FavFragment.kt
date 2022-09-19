package com.example.mymovieappassigment.Views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinroomdatabase.viewModel.UserViewModel
import com.example.mymovieappassigment.R
import com.example.mymovieappassigment.adapter.MoviesFavAdapter
import com.example.mymovieappassigment.databinding.FragmentFavBinding
import com.example.mymovieappassigment.roomDatabase.model.FavModel
import com.example.mymovieappassigment.viewModel.FirstFragmentViewModel

class FavFragment : Fragment() {

    private lateinit var popularMoviesAdapter: MoviesFavAdapter
    private lateinit var popularMoviesLayoutMgr: LinearLayoutManager
    private lateinit var viewModel: FirstFragmentViewModel
    private lateinit var binding: FragmentFavBinding
    private lateinit var mUserViewModel: UserViewModel
    val favArrayList = arrayListOf<Long>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_fav,
            container,
            false
        )

        binding.setLifecycleOwner(this)

        viewModel = ViewModelProvider(this).get(FirstFragmentViewModel::class.java)
        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        val popularMovies = binding.popularMovies

        popularMoviesLayoutMgr = GridLayoutManager(this.activity, 2)
        popularMovies.layoutManager = popularMoviesLayoutMgr
        popularMoviesAdapter =
            MoviesFavAdapter(mutableListOf(), requireContext(), mUserViewModel, favArrayList)
        popularMovies.adapter = popularMoviesAdapter

        viewModel.getMovie().observe(viewLifecycleOwner, Observer { newMovies ->
        })

        mUserViewModel.readAllData.observe(viewLifecycleOwner, Observer { user ->
            Log.i("MoviesAdapter", "move list  $user")
            onPopularMoviesFetched(user)

        })

        return binding.root

    }

    private fun onPopularMoviesFetched(movies: List<FavModel>) {
        popularMoviesAdapter.appendMovies(movies)

    }

}