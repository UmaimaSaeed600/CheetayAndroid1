package com.example.mymovieappassigment

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.kotlinroomdatabase.viewModel.UserViewModel
import com.example.mymovieappassigment.Views.PopularMovieFragmentDirections
import com.example.mymovieappassigment.Views.MainActivity.Constants.searchName
import com.example.mymovieappassigment.databinding.ItemMovieBinding
import com.example.mymovieappassigment.roomDatabase.model.FavModel

class MoviesAdapter(
    private var movies: MutableList<FavModel>,
    val context: Context,
    var mUserViewModel: UserViewModel,
    val favArrayList: ArrayList<Long> = arrayListOf<Long>(),
    var isFromSearch: Boolean?
) : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {
    var isFav: Boolean? = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding: ItemMovieBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_movie,
            parent,
            false
        )
        return MovieViewHolder(binding)
    }

    fun appendMovies(movies: List<FavModel>) {
        this.movies.addAll(movies)
        notifyItemRangeInserted(
            this.movies.size,
            movies.size - 1
        )
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])

        try {
            if (favArrayList.contains(movies.get(position).moveId)) {
                holder.favorite.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_baseline_selectedfavorite_border_24
                    )
                )
            }
            if (!favArrayList.contains(movies.get(position).moveId)) {
                holder.favorite.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_baseline_favorite_border_24
                    )
                )


            }

            if (searchName.length > 0) {
                holder.favorite.visibility = View.GONE
            } else {
                holder.favorite.visibility = View.VISIBLE

            }

        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("MoviesAdapter", "zma    " + e.message)

        }
    }

    inner class MovieViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val favorite = itemView.findViewById<View>(R.id.FavBtn) as ImageView

        fun bind(movie: FavModel) {
            binding.item = movie
            Glide.with(itemView)
                .load("https://image.tmdb.org/t/p/w92${movie.posterPath}")
                .transform(CenterCrop())
                .into(binding.itemMoviePoster)

            binding.FavBtn.setOnClickListener {
                try {
                    if (favArrayList.contains(movie.moveId)) {
                        isFav = false
                        favArrayList.remove(movie.moveId)
                        val user = FavModel(
                            movie.moveId,
                            movie.title,
                            movie.overview,
                            movie.posterPath,
                            movie.backdropPath,
                            movie.rating,
                            movie.releaseDate
                        )
                        // Add Data to database
                        mUserViewModel.deleteUser(user)
                        binding.FavBtn.setImageDrawable(
                            ContextCompat.getDrawable(
                                context,
                                R.drawable.ic_baseline_favorite_border_24
                            )
                        )

                        isFav = true
                    } else {

                        favArrayList.add(movie.moveId)
                        val user = FavModel(
                            movie.moveId,
                            movie.title,
                            movie.overview,
                            movie.posterPath,
                            movie.backdropPath,
                            movie.rating,
                            movie.releaseDate
                        )
                        // Add Data to database
                        mUserViewModel.addUser(user)
                        binding.FavBtn.setImageDrawable(
                            ContextCompat.getDrawable(
                                context,
                                R.drawable.ic_baseline_selectedfavorite_border_24
                            )
                        )

                    }
                } catch (ex: Exception) {
                    Toast.makeText(context, "move missing some file", Toast.LENGTH_SHORT).show()
                }


            }
            binding.root.setOnClickListener {
                try {
                    val direction =
                        PopularMovieFragmentDirections.actionFirstFragmentToDetailFragment(movie)
                    binding.root.findNavController().navigate(direction)
                    Log.i("MoviesAdapter", movie.title)
                } catch (e: Exception) {
                    Toast.makeText(context, "some file missing in move", Toast.LENGTH_SHORT).show()
                }


            }
        }
    }
}

//fun <E> List<E>.getId() {}
