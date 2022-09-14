package com.example.mymovieappassigment.Views

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.example.mymovieappassigment.model.Movie
import com.example.mymovieappassigment.MoviesAdapter
import com.example.mymovieappassigment.model.MoviesResponse
import com.example.mymovieappassigment.Views.MainActivity.Constants.isFromSearch
import com.example.mymovieappassigment.Views.MainActivity.Constants.searchName
import com.example.mymovieappassigment.network.SearchMoveListener
import com.example.mymovieappassigment.R
import com.example.mymovieappassigment.databinding.FragmentFirstBinding
import com.example.mymovieappassigment.viewModel.FirstFragmentViewModel

class PopularMovieFragment : Fragment(), SearchMoveListener {

    private lateinit var popularMoviesAdapter: MoviesAdapter
    private lateinit var popularMoviesLayoutMgr: LinearLayoutManager

    private lateinit var viewModel: FirstFragmentViewModel
    private lateinit var binding: FragmentFirstBinding
    private lateinit var mUserViewModel: UserViewModel
    val favArrayList = arrayListOf<Long>()
    private lateinit var movies: MutableList<Movie>


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

        movies = mutableListOf()
        popularMoviesLayoutMgr = GridLayoutManager(this.activity, 2)
        popularMovies.layoutManager = popularMoviesLayoutMgr
        popularMoviesAdapter =
            MoviesAdapter(movies, requireContext(), mUserViewModel, favArrayList, false)
        popularMovies.adapter = popularMoviesAdapter
        getMoveApiCall()

        binding.etSearchView.addTextChangedListener(textWatcher)


        binding.ivClose.setOnClickListener {
            isFromSearch = true
            movies.clear()
            popularMoviesLayoutMgr.scrollToPosition(0)
            getMoveApiCall()
            it.hideKeyboard()
            binding.etSearchView.text.clear()
        }
        return binding.root
    }

    private fun getMoveApiCall() {

        movies.clear()
        viewModel.getMovie().observe(viewLifecycleOwner) { newMovies ->
            onPopularMoviesFetched(newMovies)
            popularMoviesAdapter.notifyDataSetChanged()

        }
    }

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    private fun onPopularMoviesFetched(movies: List<Movie>) {
        popularMoviesAdapter.appendMovies(movies)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        if (isFromSearch) {
            mUserViewModel.readAllData.observe(viewLifecycleOwner, Observer { user ->
                Log.i("MoviesAdapter", "move list   " + user)
                favArrayList.clear()
                for (item in user) {
                    favArrayList.add(item.moveId)
                }
                popularMoviesAdapter.notifyDataSetChanged()
            })

        } else {
            viewModel.getSearchMovie(searchName, 1)
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    override fun getMove(response: MoviesResponse?) {
        if (response?.movies!!.isNotEmpty()) {
            isFromSearch = false
            movies.clear()
            popularMoviesLayoutMgr.scrollToPosition(0)
            onPopularMoviesFetched(response.movies)
//            etSearchView.text.clear()
            popularMoviesAdapter.notifyDataSetChanged()
        } else {
            Toast.makeText(context, "search not complete", Toast.LENGTH_SHORT).show()
        }
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            Log.d("text",p0.toString())
            searchName=p0.toString()
            if (p0!!.length >= 2) {
                viewModel.getSearchMovie(p0.toString(), 1)
            }
        }

        override fun afterTextChanged(p0: Editable?) {
        }
    }

}