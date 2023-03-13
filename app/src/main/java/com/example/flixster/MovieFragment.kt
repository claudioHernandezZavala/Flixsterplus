package com.example.flixster

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Headers
import org.json.JSONObject

// --------------------------------//
// CHANGE THIS TO BE YOUR API KEY  //
// --------------------------------//
private const val API_KEY = "1752564ca5a3252561658a9621b19a2d"

/*
 * The class for the only fragment in the app, which contains the progress bar,
 * recyclerView, and performs the network calls to the NY Times API.
 */
class MovierFragment : Fragment(), OnListFragmentInteractionListener {

    /*
     * Constructing the view
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.moviefragment, container, false)
//        val progressBar = view.findViewById<View>(R.id.progress) as ContentLoadingProgressBar
        val recyclerView = view.findViewById<View>(R.id.list) as RecyclerView
        val context = view.context
        recyclerView.layoutManager = LinearLayoutManager(context)
        updateAdapter( recyclerView)
        return view
    }

    /*
     * Updates the RecyclerView adapter with new data.  This is where the
     * networking magic happens!
     */
    private fun updateAdapter( recyclerView: RecyclerView) {
//        progressBar.show()

        // Create and set up an AsyncHTTPClient() here
        var client = AsyncHttpClient()
        var params = RequestParams()

        params["api-key"] = "a07e22bc18f5cb106bfe4cc1f83ad8ed"

        // Using the client, perform the HTTP request

        client[
                "https://api.themoviedb.org/3/movie/now_playing?api_key=1752564ca5a3252561658a9621b19a2d&language=en-US&page=1\n",
                params,
                object : JsonHttpResponseHandler()

        {
            /*
             * The onSuccess function gets called when
             * HTTP response status is "200 OK"
             */
            override fun onSuccess(
                statusCode: Int,
                headers: Headers,
                json: JsonHttpResponseHandler.JSON
            ) {
                // The wait for a response is over
//                progressBar.hide()

                //TODO - Parse JSON into Models
               val resultsJSON : JSONObject = json.jsonObject as JSONObject
                Log.d("MOVIE", json.toString())
               val MovieRawJSON : String = resultsJSON.get("results").toString()
                val gson = Gson()
                val arrayMovieType = object : TypeToken<List<MovieClass>>() {}.type
//
                val models : List<MovieClass> = gson.fromJson(MovieRawJSON,arrayMovieType) // Fix me!
                recyclerView.adapter = MoviesAdapter(models, this@MovierFragment)

                // Look for this in Logcat:
                Log.d("MOVIE", "response successful")
            }

            /*
             * The onFailure function gets called when
             * HTTP response status is "4XX" (eg. 401, 403, 404)
             */
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                t: Throwable?
            ) {
                // The wait for a response is over
//                progressBar.hide()

                // If the error is not null, log it!
                t?.message?.let {
                    Log.e("BestSellerBooksFragment", errorResponse)
                }
            }
        }
        ]


    }

    /*
     * What happens when a particular book is clicked.
     */
    override fun onItemClick(item: MovieClass) {
        Toast.makeText(context, "test: " + item.title, Toast.LENGTH_LONG).show()
    }

}
