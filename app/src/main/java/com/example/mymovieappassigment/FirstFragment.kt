package com.example.mymovieappassigment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinroomdatabase.viewModel.UserViewModel
import com.example.mymovieappassigment.databinding.FragmentFirstBinding
import com.example.mymovieappassigment.viewModel.FirstFragmentViewModel
import kotlinx.android.synthetic.main.fragment_first.*

class FirstFragment : Fragment(), SearchMoveListener {

    private lateinit var popularMoviesAdapter: MoviesAdapter
    private lateinit var moviesSearchAdapter: MoviesAdapter
    private lateinit var popularMoviesLayoutMgr: LinearLayoutManager
    private lateinit var searchMoviesLayoutMgr: LinearLayoutManager

    private lateinit var viewModel: FirstFragmentViewModel
    private lateinit var binding: FragmentFirstBinding
    private lateinit var mUserViewModel: UserViewModel
    val favArrayList = arrayListOf<Long>()
    private lateinit var movies: MutableList<Movie>
    private lateinit var searchMovies: MutableList<Movie>


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_first,
            container,
            false
        )

        binding.setLifecycleOwner(this)

        viewModel = ViewModelProvider(this).get(FirstFragmentViewModel::class.java)
        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        viewModel.searchMoveListener = this

        val popularMovies = binding.popularMovies
        val searchMovies = binding.searchMovies

        movies = mutableListOf()
        popularMoviesLayoutMgr = GridLayoutManager(this.activity, 2)
        popularMovies.layoutManager = popularMoviesLayoutMgr
        popularMoviesAdapter =
            MoviesAdapter(movies, requireContext(), mUserViewModel, favArrayList, false)
        popularMovies.adapter = popularMoviesAdapter
        getMoveApiCall()



        searchMoviesLayoutMgr = GridLayoutManager(this.activity, 2)
        moviesSearchAdapter =
            MoviesAdapter(movies, requireContext(), mUserViewModel, favArrayList, true)
        searchMovies.layoutManager = searchMoviesLayoutMgr
        searchMovies.adapter = moviesSearchAdapter

        binding.ivClose.setOnClickListener {
            binding.searchMovies.visibility = View.GONE
            binding.ivClose.visibility = View.GONE
            binding.ivSearchNew.visibility = View.VISIBLE
        }

        binding.ivSearchNew.setOnClickListener {

            if (etSearchView.text.length > 1) {
                viewModel.getSearchMovie(etSearchView.text.toString(), 1)
                it.hideKeyboard()
            } else {
                Toast.makeText(context, "please enter move name", Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }

    private fun getMoveApiCall() {

        viewModel.getMovie().observe(viewLifecycleOwner) { newMovies ->
            onPopularMoviesFetched(newMovies)
        }
    }

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    private fun onPopularMoviesFetched(movies: List<Movie>) {
        popularMoviesAdapter.appendMovies(movies)
    }

    override fun onResume() {
        super.onResume()

        mUserViewModel.readAllData.observe(viewLifecycleOwner, Observer { user ->
            Log.i("MoviesAdapter", "move list   " + user)
            favArrayList.clear()
            for (item in user) {
                favArrayList.add(item.moveId)
            }
            popularMoviesAdapter.notifyDataSetChanged()
        })

    }


    @SuppressLint("NotifyDataSetChanged")
    override fun getMove(response: MoviesResponse?) {
        if (response?.movies!!.isNotEmpty()) {
            movies.clear()
            binding.searchMovies.visibility = View.VISIBLE
            binding.ivClose.visibility = View.VISIBLE
            binding.ivSearchNew.visibility = View.GONE
            moviesSearchAdapter.appendMovies(response.movies)
            etSearchView.text.clear()
            moviesSearchAdapter.notifyDataSetChanged()
        } else {
            Toast.makeText(context, "search not complete", Toast.LENGTH_SHORT)
                .show()

        }
    }

}