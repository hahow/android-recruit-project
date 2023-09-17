package shawn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import `in`.hahow.android_recruit_project.R
import shawn.ui.class_schedule.ClassScheduleActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ClassScheduleActivity.start(this)
    }
}