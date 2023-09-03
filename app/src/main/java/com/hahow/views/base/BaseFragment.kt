package com.hahow.views.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    private var _binding: VB? = null

    /**
     * 由於是掌管 View ，因此僅在 onViewCreated ~ onDestroyView 之間使用
     * */
    val mBinding
        get() = _binding
            ?: throw RuntimeException("Should only use binding after onCreateView and before onDestroyView")


    /**
     * 建立 ViewBinding 以達到在 onCreateView 綁定。
     * */
    abstract fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        boolean: Boolean
    ): VB

    protected fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) {
        _binding = createViewBinding(inflater, container, false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initBinding(inflater, container, savedInstanceState)
        return mBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * 進行 View 相關的加載及初始化
     * */
    protected open fun VB.initView() {}

    /**
     * 負責綁定 ViewModel.StateFlow/SharedFlow 跟 View 之間的關係，
     * */
    protected open fun observeData() {}

    /**
     * 如果在 initView, observeData 完成前置作業，即在 initAction 處理加載 API 或需執行的資料流等事項。
     * */
    protected open fun initAction() {}

}