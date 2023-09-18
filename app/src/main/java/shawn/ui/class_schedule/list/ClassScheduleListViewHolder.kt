package shawn.ui.class_schedule.list

import androidx.recyclerview.widget.RecyclerView
import `in`.hahow.android_recruit_project.databinding.ItemClassScheduleListBinding

class ClassScheduleListViewHolder(private val binding: ItemClassScheduleListBinding) :
    RecyclerView.ViewHolder(binding.root) {

        fun setData(data: ClassSchedule){
            binding.tvTitle.text = "測試中"
            binding.tvGoal.text = "12%"
            binding.tvTime.text = "倒數三天"
            binding.tvCoin.text = "好幣 2,000 MP"
            binding.tvStatus.text = "募資中"

        }
}