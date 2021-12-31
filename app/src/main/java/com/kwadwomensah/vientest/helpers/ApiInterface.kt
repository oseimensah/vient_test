package com.kwadwomensah.vientest.helpers

import com.kwadwomensah.vientest.model.PostModel
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
  @GET("posts")
  fun getAllPosts() : Call<List<PostModel>>
}