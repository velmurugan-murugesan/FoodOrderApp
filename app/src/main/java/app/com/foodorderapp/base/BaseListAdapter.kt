package app.com.foodorderapp.base

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import app.com.foodorderapp.data.callback.OnListItemClickListener
import java.util.*

abstract class BaseListAdapter<VH : RecyclerView.ViewHolder, VO> protected constructor(val context: Context) : RecyclerView.Adapter<VH>() {

    private val mAllItems = LinkedList<VO?>()
    protected val layoutInflater: LayoutInflater = LayoutInflater.from(context)
    private var mItemClickListener: OnListItemClickListener<VO>? = null

    val allItems: List<VO?>
        get() = mAllItems

    fun setItemClickListener(itemClickListener: OnListItemClickListener<VO>) {
        mItemClickListener = itemClickListener
    }

    fun getItemClickListener(): OnListItemClickListener<VO>? {
        return mItemClickListener
    }

    fun setItems(items: List<VO>) {
        mAllItems.clear()
        mAllItems.addAll(items)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        mAllItems.removeAt(position)
        notifyItemRemoved(position)
    }

    fun appendItems(items: List<VO>) {
        val size = mAllItems.size
        mAllItems.addAll(items)
        notifyItemRangeInserted(size, mAllItems.size)
    }

    fun appendItems(position: Int, items: List<VO>) {
        val size = mAllItems.size
        mAllItems.addAll(position, items)
        notifyItemRangeInserted(size, mAllItems.size)
    }

    fun appendItem(position: Int, item: VO?) {
        mAllItems.add(position, item)
        notifyItemInserted(position)
    }

    fun appendItem(item: VO?) {
        mAllItems.add(item)
        notifyItemInserted(mAllItems.size)
    }

    fun clearItems() {
        mAllItems.clear()
        notifyDataSetChanged()
    }

    fun getItem(index: Int): VO? {
        return mAllItems[index]
    }

    override fun getItemCount(): Int {
        return mAllItems.size
    }

    open inner class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal open fun bind(item: T?) {}

        internal fun setClickListener(vararg views: View) {
            for (view in views) {
                view.setOnClickListener(clickListener)
            }
        }

        private val clickListener = View.OnClickListener { v ->
            val position = adapterPosition
            mItemClickListener?.onItemClick(v, getItem(position), position)
        }

    }
}
