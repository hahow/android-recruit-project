package com.hahow.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import `in`.hahow.android_recruit_project.R


fun ImageView.loadUrl(url: String, placeholder: Int = R.drawable.img_placeholder) {
    val requestOptions = RequestOptions()
        .transform(RoundedCorners(20))
    Glide.with(this)
        .load(url)
        .placeholder(placeholder)
        .apply(requestOptions)
        .into(this)
}