package com.hahow.ui

import android.content.Context
import com.hahow.dataModel.Course
import com.hahow.domain.ProductStatus
import `in`.hahow.android_recruit_project.R
import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneId
import org.threeten.bp.temporal.ChronoUnit

/**
 * @param titleText 標題文字
 * @param statusText 狀態文字
 * @param imageUrl 圖片網址
 * @param dueTimeText 到期時間文字
 * @param ticketSoldOutText 銷售百分比文字
 * @param ticketSoldOutPercent 銷售百分比(progress bar)
 * @param ticketSoldOutProgressColor 銷售百分比(progress bar)顏色
 * @param statusColor 狀態文字顏色
 * */
data class CourseCellViewInfo(
    val titleText: String = "",
    val statusText: String = "",
    val statusColor: Int = R.color.purple,
    val imageUrl: String = "",
    val dueTimeText: String? = null,
    val ticketSoldOutText: String = "",
    val ticketSoldOutPercent: Int? = null,
    val ticketSoldOutProgressColor: Int = R.color.purple
)

/**
 * 思考可能有多種情境會使用到 CourseCellModel，
 * 每次配合的資料可能不同，因此將資料轉換的邏輯抽出來，
 * 未來要用到相同的 UI (CourseCellModel) 時，直接轉成 ViewInfo 即可完成擴增
 * */
class CourseCellViewInfoConverter {
    fun convert(context: Context, course: Course): CourseCellViewInfo {
        return CourseCellViewInfo(
            titleText = course.title,
            imageUrl = course.coverImageUrl,
            statusText = getStatusText(context, course.productStatus),
            statusColor = getStatusColor(course.productStatus),
            dueTimeText = getDueTimeText(context, course),
            ticketSoldOutText = getTicketSoldOutText(context, course),
            ticketSoldOutPercent = getTicketSoldOutPercent(course),
            ticketSoldOutProgressColor = getTicketSoldOutProgressColor(course)
        )
    }

    private fun getStatusText(context: Context, status: ProductStatus): String {
        return when (status) {
            ProductStatus.INCUBATING -> context.getString(R.string.product_status_incubating)
            ProductStatus.PUBLISHED -> context.getString(R.string.product_status_published)
            ProductStatus.SUCCESS -> context.getString(R.string.product_status_success)
        }
    }

    private fun getDueTimeText(context: Context, course: Course): String? {
        return when {
            course.proposalDueTime.isNullOrEmpty() -> null
            else -> {
                val instant: Instant = Instant.parse(course.proposalDueTime)
                val zone: ZoneId = ZoneId.systemDefault()
                val dueDate: LocalDate = instant.atZone(zone).toLocalDate()
                val differenceData = ChronoUnit.DAYS.between(LocalDate.now(), dueDate)
                return context.getString(R.string.countdown_days, differenceData)
            }
        }
    }

    private fun getStatusColor(status: ProductStatus): Int {
        return when (status) {
            ProductStatus.INCUBATING -> R.color.orange
            ProductStatus.PUBLISHED -> R.color.teal
            ProductStatus.SUCCESS -> R.color.blue
        }
    }

    private fun getTicketSoldOutText(context: Context, course: Course): String {
        return if (course.isSoldOut)
            context.getString(R.string.sold_out_percentage, course.soldTicketPercentage)
        else "${course.numSoldTickets} / ${course.successCriteria.numSoldTickets}"

    }

    private fun getTicketSoldOutPercent(course: Course): Int {
        return (course.numSoldTickets.toFloat() / course.successCriteria.numSoldTickets * 100).toInt()
    }

    private fun getTicketSoldOutProgressColor(course: Course): Int {
        return if (course.isSoldOut) R.color.teal else R.color.orange
    }

}