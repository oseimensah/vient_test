package com.kwadwomensah.vientest.helpers

import android.util.Log
import com.kwadwomensah.vientest.model.PostModel
import java.util.*

class Paginator {
  fun generatePage(currentPage: Int, posts: List<PostModel>?) : ArrayList<PostModel> {
    // total number of posts
    val totalNumOfPost: Int = posts!!.size
    // post item remaining after total post is divided by the number of items per page
    val itemRemaining: Int =
      totalNumOfPost % Companion.itemPerPage
    // last page number after total post is divided by number of item per page
    val lastPage: Int =
      totalNumOfPost / Companion.itemPerPage
    // the initial page
    val startItem: Int = currentPage * Companion.itemPerPage + 1
    val numOfData: Int = Companion.itemPerPage

    val pageData = ArrayList<PostModel>()

    // check if current page is equal to last page and items...
    // ...remaining is greater or equal to items per page
    if (currentPage == lastPage && itemRemaining >= Companion.itemPerPage ) {
      for (i in startItem until startItem + itemRemaining) {
        pageData.add(posts[i])
      }
    }
    else if(currentPage == lastPage && itemRemaining < Companion.itemPerPage){
      for (i in startItem until startItem + itemRemaining-1){
        pageData.add(posts[i])
      }
    }
    else {
      for (i in startItem until startItem + numOfData) {
        pageData.add(posts[i])
      }
    }
    return pageData
  }

  companion object {
    const val itemPerPage = 6
  }
}
