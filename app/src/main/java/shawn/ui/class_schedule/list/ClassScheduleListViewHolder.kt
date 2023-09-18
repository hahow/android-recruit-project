package shawn.ui.class_schedule.list

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import `in`.hahow.android_recruit_project.R
import `in`.hahow.android_recruit_project.databinding.ItemClassScheduleListBinding
import shawn.util.GlideApp
import shawn.util.UiUtils

class ClassScheduleListViewHolder(private val binding: ItemClassScheduleListBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun setData(data: ClassSchedule) {

        binding.apply {

        }
        binding.tvTitle.text = "測試中"
        binding.tvGoal.text = "12%"
        binding.tvTime.text = "倒數三天"
        binding.tvCoin.text = "好幣 2,000 MP"
        binding.tvStatus.text = "募資中"
        binding.progressBar.progress = 25

        loadCoverImage(binding.ivCover,
            "https://images.api.hahow.in/images/614eca15a39712000619b672","")
    }

    private fun loadCoverImage(view: ImageView?, imgUrl: String, imgFileName: String?) {
       view?.let {
           val context = view.context

           GlideApp.with(context)
               .load(imgUrl)
               .transform(CenterCrop(), RoundedCorners(UiUtils.dpToPx(10)))
               .error(
                   Glide.with(context).load(R.drawable.ic_launcher_background).error(R.drawable
                       .ic_launcher_background)
               )
               .into(view)
       }
    }
}