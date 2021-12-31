package com.kwadwomensah.vientest.helpers

import com.kwadwomensah.vientest.model.PostModel

interface CellClickListener {
  // function to allow click action on the recyclerview item
  fun onCellClickListener(data: PostModel)
}