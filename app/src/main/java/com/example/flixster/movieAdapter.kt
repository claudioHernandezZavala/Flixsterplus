package com.example.flixster

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.flixster.R.id

class MoviesAdapter(
    private val movies: List<MovieClass>,
    private val mListener: OnListFragmentInteractionListener?
)
    : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_layout, parent, false)
        return MovieViewHolder(view)
    }

    /**
     * This inner class lets us refer to all the different View elements
     * (Yes, the same ones as in the XML layout files!)
     */
    inner class MovieViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        var mItem: MovieClass? = null
        val mTitle: TextView = mView.findViewById<View>(id.movieTitlel)as TextView
        val mDescription: TextView = mView.findViewById<View>(id.MovieDesc) as TextView
        val mBookImage: ImageView = mView.findViewById<View>(id.movieUrl) as ImageView

        override fun toString(): String {
            return mTitle.toString() + " '"
        }
    }

    /**
     * This lets us "bind" each Views in the ViewHolder to its' actual data!
     */
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]

        holder.mItem = movie
        holder.mTitle.text = movie.title
        holder.mDescription.text = movie.overview

        holder.mView.setOnClickListener {
            holder.mItem?.let { book ->
                mListener?.onItemClick(book)
            }
        }

        var finalUrl = "https://image.tmdb.org/t/p/w500/"

        Glide.with(holder.mView)
            .load(finalUrl+movie.backdrop_path).placeholder(R.drawable.ic_launcher_foreground)
            .centerInside()
            .centerCrop() // scale to fill the ImageView and crop any extra
            .into(holder.mBookImage)
    }

    /**
     * Remember: RecyclerView adapters require a getItemCount() method.
     */
    override fun getItemCount(): Int {
        return movies.size
    }
}