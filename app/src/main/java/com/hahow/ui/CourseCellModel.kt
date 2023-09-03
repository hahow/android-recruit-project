package com.hahow.ui

import androidx.core.content.ContextCompat
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.hahow.extensions.loadUrl
import com.hahow.ui.base.ViewBindingEpoxyModelWithHolder
import `in`.hahow.android_recruit_project.R
import `in`.hahow.android_recruit_project.databinding.AdapterCourseCellBinding

@EpoxyModelClass(layout = R.layout.adapter_course_cell)
abstract class CourseCellModel : ViewBindingEpoxyModelWithHolder<AdapterCourseCellBinding>() {

    @EpoxyAttribute
    lateinit var viewInfo: CourseCellViewInfo

    @EpoxyAttribute
    var listener: CourseCellListener? = null

    override fun AdapterCourseCellBinding.bind() {
        val context = root.context

        ivImage.apply {
            loadUrl(viewInfo.imageUrl)
        }

        tvTitle.apply {
            text = viewInfo.titleText
        }

        tvDueTime.apply {
            text = viewInfo.dueTimeText
        }

        tvStatus.apply {
            text = viewInfo.statusText
            background.setTint(ContextCompat.getColor(context, viewInfo.statusColor))
        }

        tvTicketSoldOutInfo.apply {
            text = viewInfo.ticketSoldOutText
        }

        pbTicketSoldOut.apply {
            pbTicketSoldOut.progress = viewInfo.ticketSoldOutPercent ?: 0
            val color = ContextCompat.getColor(context, viewInfo.ticketSoldOutProgressColor)
            pbTicketSoldOut.setIndicatorColor(color)
        }

        root.setOnClickListener { listener?.onProductCellClick(viewInfo.titleText) }
    }

    override fun AdapterCourseCellBinding.unbind() {

    }

    override fun AdapterCourseCellBinding.onViewHolderBinding() {
        ivImage.layoutParams.apply {
            width = root.resources.displayMetrics.widthPixels * 4 / 10
        }
        pbTicketSoldOut.layoutParams.apply {
            width = root.resources.displayMetrics.widthPixels * 2 / 10
        }
    }
}

interface CourseCellListener {
    fun onProductCellClick(title: String)
}