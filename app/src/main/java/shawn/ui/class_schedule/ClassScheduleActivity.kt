package shawn.ui.class_schedule

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import `in`.hahow.android_recruit_project.R
import `in`.hahow.android_recruit_project.databinding.ActivityClassScheduleBinding
import shawn.ui.class_schedule.list.ClassScheduleListFragment

class ClassScheduleActivity : AppCompatActivity(), ClassScheduleActivityHandler {

    private lateinit var binding: ActivityClassScheduleBinding

    companion object {
        fun start(context: Context?) {
            context?.let {
                val intent = Intent(context, ClassScheduleActivity::class.java)
                context.startActivity(intent)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClassScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        goClassScheduleList()
    }

    override fun goClassScheduleList() {
        supportFragmentManager.commit {
            replace(R.id.frame_layout, ClassScheduleListFragment.newInstance())
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }
}