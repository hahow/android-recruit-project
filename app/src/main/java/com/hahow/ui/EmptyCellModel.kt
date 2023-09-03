package com.hahow.ui

import com.airbnb.epoxy.EpoxyModelClass
import com.hahow.ui.base.ViewBindingEpoxyModelWithHolder
import `in`.hahow.android_recruit_project.R
import `in`.hahow.android_recruit_project.databinding.AdapterEmptyCellBinding

@EpoxyModelClass(layout = R.layout.adapter_empty_cell)
abstract class EmptyCellModel : ViewBindingEpoxyModelWithHolder<AdapterEmptyCellBinding>() {
    override fun AdapterEmptyCellBinding.bind() {}

}