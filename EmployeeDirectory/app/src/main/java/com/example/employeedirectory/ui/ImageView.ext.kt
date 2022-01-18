@file:JvmName("ImageViewExtension")

package com.example.employeedirectory.ui

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.ViewTarget
import com.example.employeedirectory.R

fun ImageView.loadImage(url: String): ViewTarget<ImageView, Bitmap> {
    val circularProgressDrawable = CircularProgressDrawable(this.context)
    circularProgressDrawable.strokeWidth = 75f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.start()
    return Glide.with(this).asBitmap().load(url).centerCrop()
        .placeholder(circularProgressDrawable)
        .error(R.drawable.ic_launcher_foreground)
        .fallback(R.drawable.ic_launcher_foreground).into(this)
}
