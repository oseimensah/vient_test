package com.kwadwomensah.vientest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kwadwomensah.vientest.adapter.MyRecyclerAdapter
import com.kwadwomensah.vientest.helpers.ApiInterface
import com.kwadwomensah.vientest.helpers.CellClickListener
import com.kwadwomensah.vientest.helpers.Paginator
import com.kwadwomensah.vientest.model.PostModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), CellClickListener {

    var rv: RecyclerView? = null
    var nextBtn: Button? = null
    var prevBtn: Button? = null
    var retryBtn: Button? = null
    var errorText: TextView? = null
    var progressBar: ProgressBar? = null
    var postsData: List<PostModel>? = null
    var paginator = Paginator()

    private var totalPages : Int? = null
    private var currentPage = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
    }

    private fun initViews() {
        //RFERENCE VIEWS
        rv = findViewById<View>(R.id.postList) as RecyclerView
        nextBtn = findViewById<View>(R.id.nextButton) as Button
        prevBtn = findViewById<View>(R.id.prevButton) as Button
        retryBtn = findViewById<View>(R.id.postRetry) as Button
        errorText = findViewById<View>(R.id.postErrorMessage) as TextView
        progressBar = findViewById<View>(R.id.postLoading) as ProgressBar

        prevBtn!!.setEnabled(false)

        getCurrentPosts()

        //RECYCLER PROPERTIES
        rv!!.layoutManager = LinearLayoutManager(this)

        //NAVIGATE
        nextBtn!!.setOnClickListener {
            currentPage += 1
            rv!!.adapter = MyRecyclerAdapter(this@MainActivity, paginator.generatePage(currentPage, postsData), this@MainActivity)
            toggleButtons()
        }

        prevBtn!!.setOnClickListener{
            currentPage -= 1
            rv!!.adapter = MyRecyclerAdapter(this@MainActivity, paginator.generatePage(currentPage, postsData), this@MainActivity)
            toggleButtons()
        }

        retryBtn!!.setOnClickListener {
            getCurrentPosts()
        }
    }

    private fun setVisibilities(isLoading: Boolean, isError: Boolean?) {
        if (isLoading){
            this@MainActivity.runOnUiThread {
                progressBar!!.visibility = View.VISIBLE
                rv!!.visibility = View.GONE
                retryBtn!!.visibility = View.GONE
                errorText!!.visibility = View.GONE
                nextBtn!!.visibility = View.GONE
                prevBtn!!.visibility = View.GONE
            }
        }
        else if (isError!!){
            rv!!.visibility = View.GONE
            progressBar!!.visibility = View.GONE
            retryBtn!!.visibility = View.VISIBLE
            errorText!!.visibility = View.VISIBLE
            nextBtn!!.visibility = View.GONE
            prevBtn!!.visibility = View.GONE
        }
        else {
            rv!!.visibility = View.VISIBLE
            progressBar!!.visibility = View.GONE
            retryBtn!!.visibility = View.GONE
            errorText!!.visibility = View.GONE
            nextBtn!!.visibility = View.VISIBLE
            prevBtn!!.visibility = View.VISIBLE
        }
    }

    private fun toggleButtons() {
        if (currentPage == totalPages) {
            nextBtn!!.setEnabled(false)
            prevBtn!!.setEnabled(true)
        } else if (currentPage == 0) {
            prevBtn!!.setEnabled(false)
            nextBtn!!.setEnabled(true)
        } else if (currentPage >= 1 && currentPage <= totalPages!!) {
            nextBtn!!.setEnabled(true)
            prevBtn!!.setEnabled(true)
        }
    }

    internal fun getCurrentPosts() {
        setVisibilities(true, false)
        val retrofit = Retrofit.Builder()
            .baseUrl(BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(ApiInterface::class.java)
        val call = service.getAllPosts()

        call.enqueue(object : Callback<List<PostModel>> {
            override fun onResponse(call: Call<List<PostModel>>, response: Response<List<PostModel>>) {
                postsData = response.body()
                totalPages = postsData!!.size / Paginator.itemPerPage
                //ADAPTER
                rv!!.adapter = MyRecyclerAdapter(this@MainActivity, paginator.generatePage(currentPage,
                    postsData
                ), this@MainActivity)
                setVisibilities(false, false)
            }

            override fun onFailure(call: Call<List<PostModel>>, t: Throwable) {
                Log.e("MainActivity", "onFailure: $t" )
                errorText!!.text = "Connectivity issue. Please connect and retry"
                setVisibilities(false, true)
            }
        })

    }

    companion object {
        val BaseUrl = "https://jsonplaceholder.typicode.com/"
    }

    override fun onCellClickListener(data: PostModel) {
        Toast.makeText(this, "${data.title}", Toast.LENGTH_LONG).show()
        val detailIntent = Intent(this,PostDetail::class.java).apply {
            putExtra("title", data.title)
            putExtra("detail", data.body)
        }
        startActivity(detailIntent)
    }
}