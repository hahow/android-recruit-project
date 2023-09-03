package com.hahow.views.home.adapter

import com.airbnb.epoxy.EpoxyController
import com.hahow.ui.CourseCellListener
import com.hahow.ui.CourseCellModel
import com.hahow.ui.CourseCellModel_
import com.hahow.ui.CourseCellViewInfo
import com.hahow.ui.EmptyCellModel
import com.hahow.ui.EmptyCellModel_

/**
 * EpoxyController 可當作簡化多種 ViewType 的工具
 * 具備 DiffUtil 的功能及類似宣告式的寫法
 * */
class HomeViewController : EpoxyController() {

    /**
     * 當更新資料時，呼叫 requestModelBuild() 即可更新畫面
     * */
    var courseCellViewInfoList = listOf<CourseCellViewInfo>()
        set(value) {
            field = value
            requestModelBuild()
        }

    var courseCellListener: CourseCellListener? = null
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildModels() {
        courseCellViewInfoList.forEachIndexed { index, viewInfo ->
            CourseCellModel_()
                .id(CourseCellModel::class.java.simpleName + index)
                .viewInfo(viewInfo)
                .listener(courseCellListener)
                .addIf(courseCellViewInfoList.isNotEmpty(), this)
        }

        /*
        * 當沒有資料時，顯示空白頁面
        * */
        EmptyCellModel_()
            .id(EmptyCellModel::class.java.simpleName)
            .addIf(courseCellViewInfoList.isEmpty(), this)

    }
}