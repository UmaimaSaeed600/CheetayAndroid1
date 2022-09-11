package com.example.mymovieappassigment.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.kotlinroomdatabase.viewModel.UserViewModel
import com.example.mymovieappassigment.FavFragmentDirections
import com.example.mymovieappassigment.R
import com.example.mymovieappassigment.databinding.ItemFavMovieBinding
import com.example.mymovieappassigment.roomDatabase.model.FavModel

class MoviesFavAdapter(
    private var movies: MutableList<FavModel>,
    val context: Context,
    var mUserViewModel: UserViewModel,
    val favArrayList: ArrayList<Long> = arrayListOf<Long>()

) : RecyclerView.Adapter<MoviesFavAdapter.MovieViewHolder>() {
    var isFav: Boolean? = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding: ItemFavMovieBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_fav_movie,
            parent,
            false
        )
        return MovieViewHolder(binding)
    }

    fun appendMovies(movies: List<FavModel>) {
        this.movies.addAll(movies)
        notifyItemRangeInserted(
            this.movies.size,
            movies.size - 0
        )
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])


    }


    @Suppress("DEPRECATION")
    inner class MovieViewHolder(private val binding: ItemFavMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val favorite = itemView.findViewById<View>(R.id.FavBtn) as ImageView

        fun bind(movie: FavModel) {
            binding.item = movie
            Glide.with(itemView)
                .load("https://image.tmdb.org/t/p/w92${movie.posterPath}")
                .transform(CenterCrop())
                .into(binding.itemMoviePoster)

            Log.i("MoviesAdapter", (favArrayList + "").toString())

//

            binding.FavBtn.setOnClickListener {
                val user = FavModel(movie.moveId,movie.title,movie.posterPath,movie.backdropPath,movie.releaseDate,movie.rating,movie.overview)

                // Add Data to database
                mUserViewModel.deleteUser(user)
//                movies.removeAt(position)
//                notifyItemRemoved(position)
                movies.clear()
//                notifyItemRangeChanged(position, movies.size)
                notifyDataSetChanged()
            }
            binding.root.setOnClickListener {
                val direction = FavFragmentDirections.actionFirstFragmentToDetailFragment(movie)
                binding.root.findNavController().navigate(direction)
                Log.i("MoviesAdapter", movie.title)

            }


//
        }
    }
}

//fun <E> List<E>.getId() {}

