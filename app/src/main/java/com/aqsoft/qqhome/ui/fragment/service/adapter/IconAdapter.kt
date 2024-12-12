package com.aqsoft.qqhome.ui.fragment.service.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView

class IconAdapter(
    private val context: Context,
    private val icons: List<Int>
) : BaseAdapter() {

    override fun getCount(): Int = icons.size

    override fun getItem(position: Int): Any = icons[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val imageView: ImageView = convertView as? ImageView ?: ImageView(context)
        imageView.setImageResource(icons[position])
        imageView.adjustViewBounds = true
        imageView.layoutParams = ViewGroup.LayoutParams(120,120)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        return imageView
    }
}