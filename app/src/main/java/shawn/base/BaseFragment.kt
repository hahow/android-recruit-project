package shawn.base

import android.content.Context
import androidx.fragment.app.Fragment

open class BaseFragment : Fragment(), BaseViewInterface {

    private var activityHandler: Context? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activityHandler = context
    }

    override fun onDetach() {
        super.onDetach()
        activityHandler = null
    }

    fun <H> getActivityHandler(type: Class<H>?): H? {
        if (type != null && type.isInstance(activityHandler)) {
            return activityHandler as H
        }
        return null
    }

    override fun getFragmentContext(): Context {
        return requireContext()
    }
}