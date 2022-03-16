package com.image.viewer.demo.utilities

import android.content.Context
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target


fun Context?.showToast(msg: String?) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

/**
 * Loads thumbnail.
 */
fun loadThumb(image: ImageView?, thumbId: Int) {
    // We don't want Glide to crop or resize our image
    val options = RequestOptions()
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .override(Target.SIZE_ORIGINAL)
        .dontTransform()
    Glide.with(image!!)
        .load(thumbId)
        .apply(options)
        .into(image)
}

/**
 * Loads thumbnail and then replaces it with full image.
 */
fun loadFull(image: ImageView?, imageId: Int, thumbId: Int) {
    // We don't want Glide to crop or resize our image
    val options = RequestOptions()
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .override(Target.SIZE_ORIGINAL)
        .dontTransform()
    val thumbRequest = Glide.with(image!!)
        .load(thumbId)
        .apply(options)
    Glide.with(image)
        .load(imageId)
        .apply(options)
        .thumbnail(thumbRequest)
        .into(image)
}

fun clear(view: ImageView) {
    // Clearing current Glide request (if any)
    Glide.with(view).clear(view)
    // Cleaning up resources
    view.setImageDrawable(null)
}
