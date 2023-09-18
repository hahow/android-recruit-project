package shawn.ui.class_schedule.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import `in`.hahow.android_recruit_project.databinding.ItemClassScheduleListBinding
import java.io.Serializable

class ClassScheduleListAdapter : ListAdapter<ClassSchedule, ClassScheduleListViewHolder>(DiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassScheduleListViewHolder {
        return ClassScheduleListViewHolder(
            ItemClassScheduleListBinding.inflate(
                LayoutInflater
                    .from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ClassScheduleListViewHolder, position: Int) {
        currentList[position]?.let {
            holder.setData(it)
        }
    }

    class DiffCallBack : DiffUtil.ItemCallback<ClassSchedule>() {
        override fun areItemsTheSame(oldItem: ClassSchedule, newItem: ClassSchedule): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ClassSchedule, newItem: ClassSchedule): Boolean {
            return oldItem.name == newItem.name
        }

    }
}


data class ClassSchedule(
    val name: String,
    val value: String
) : Serializable