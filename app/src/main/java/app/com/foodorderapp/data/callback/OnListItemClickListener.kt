package app.com.foodorderapp.data.callback

import android.view.View

interface OnListItemClickListener<T> {
    fun onItemClick(view: View?, model: T, position: Int) : Boolean
}