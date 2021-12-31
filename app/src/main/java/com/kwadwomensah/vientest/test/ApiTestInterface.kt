package com.kwadwomensah.vientest.test

import com.kwadwomensah.vientest.model.Post
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiTestInterface {
  // api get post request
  @GET("posts")
  fun getAllPosts() : Call<List<Post>>

  companion object {
    // base url for request
    val baseUrl = "https://jsonplaceholder.typicode.com/"

    // retrofit builder for making request
    fun create() : ApiTestInterface {
      val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

      return retrofit.create(ApiTestInterface::class.java)
    }
  }
}