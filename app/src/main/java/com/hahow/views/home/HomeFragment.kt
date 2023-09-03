package com.hahow.views.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.hahow.ui.dialog.AlertDialogFactory
import com.hahow.ui.CourseCellListener
import com.hahow.ui.CourseCellViewInfoConverter
import com.hahow.views.base.BaseFragment
import com.hahow.views.home.adapter.HomeViewController
import `in`.hahow.android_recruit_project.databinding.FragmentHomeBinding
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val viewModel by viewModel<HomeViewModel>()
    private val homeViewInput: HomeViewInput get() = viewModel
    private val homeViewOutput: HomeViewOutput get() = viewModel

    private val controller by lazy {
        HomeViewController().apply {
            this.courseCellListener = this@HomeFragment.courseCellListener
        }
    }

    private val viewInfoConverter by lazy { CourseCellViewInfoConverter() }

    private val loadingDialog by lazy { AlertDialogFactory.showLoadingDialog(requireContext()) }

    private val courseCellListener by lazy {
        object : CourseCellListener {
            override fun onProductCellClick(title: String) {
                homeViewInput.onCourseCellClick(title)
            }
        }
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        boolean: Boolean
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, boolean)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.initView()
        observeData()
        initAction()
    }

    override fun FragmentHomeBinding.initView() {

        spCourseList.setOnRefreshListener {
            homeViewInput.onRefreshing()
        }

        rvCourseList.apply {
            adapter = controller.adapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    /**
     * 當接收到 observe 資料時，將 dataModel 轉換成 viewInfo
     * 專門供 UI 使用
     * */
    override fun observeData() {
        super.observeData()
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            homeViewOutput.courseCellStateFlow.collect { productList ->
                val viewInfo = productList.map { viewInfoConverter.convert(requireContext(), it) }
                controller.courseCellViewInfoList = viewInfo
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            homeViewOutput.isLoadingStateFlow.collect { isLoadingCount ->
                if (isLoadingCount > 0) {
                    loadingDialog.show()
                } else {
                    loadingDialog.dismiss()
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            homeViewOutput.homeViewStateFlow.collect { viewState ->
                when (viewState) {
                    is HomeViewState.ShowToast -> {
                        Toast.makeText(requireContext(), viewState.message, Toast.LENGTH_SHORT)
                            .show()
                    }

                    is HomeViewState.ShowDialog -> {
                        AlertDialogFactory.showAlertDialog(requireContext(), viewState.message)
                            .show()
                    }

                    is HomeViewState.Loaded -> {
                        mBinding.spCourseList.isRefreshing = false
                    }
                }
            }
        }
    }

    override fun initAction() {
        super.initAction()
        homeViewInput.onViewCreated()
    }
}