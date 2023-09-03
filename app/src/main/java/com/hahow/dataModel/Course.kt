package com.hahow.dataModel

import com.hahow.domain.ProductStatus

/**
 * @param title 標題
 * @param status 募資狀態
 * @param proposalDueTime 募資截止時間
 * @param numSoldTickets 已售出票數
 * @param coverImageUrl 封面圖片網址
 * @param successCriteria 募資人數目標
 * */
data class Course(
    val title: String = "",
    private val status: String = "",
    val proposalDueTime: String? = null,
    val numSoldTickets: Int = 0,
    val coverImageUrl: String = "",
    val successCriteria: SuccessCriteria = SuccessCriteria(),
) {

    val productStatus: ProductStatus
        get() = when (status) {
            "INCUBATING" -> ProductStatus.INCUBATING
            "PUBLISHED" -> ProductStatus.PUBLISHED
            "SUCCESS" -> ProductStatus.SUCCESS
            else -> ProductStatus.INCUBATING
        }

    val isSoldOut: Boolean
        get() = numSoldTickets >= successCriteria.numSoldTickets

    val soldTicketPercentage: Double
        get() = ((numSoldTickets.toDouble() / successCriteria.numSoldTickets.toDouble()) * 100)

    /**
     * @param numSoldTickets 目標售出票數
     * */
    data class SuccessCriteria(
        val numSoldTickets: Int = 0,
    )

}
