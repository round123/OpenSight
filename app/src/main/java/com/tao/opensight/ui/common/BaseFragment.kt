package com.tao.opensight.ui.common

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.tao.opensight.ext.hideStatusBar
import com.tao.opensight.ext.logD

/**
 * 应用程序中所有Fragment的基类。
 *
 */
abstract class BaseFragment<VB:ViewBinding> : Fragment() {

    /**
     * 是否已经加载过数据
     */
    private var mHasLoadedData = false

    /**
     * 依附的Activity
     */
    lateinit var activity: Activity

    /**
     * 日志输出标志
     */
    protected val TAG: String = this.javaClass.simpleName

    protected lateinit var mBinding: VB
    protected abstract fun initViewBinding(): VB

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // 缓存当前依附的activity
        activity = requireActivity()
        logD(TAG, "BaseFragment-->onAttach()")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logD(TAG, "BaseFragment-->onCreate()")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        logD(TAG, "BaseFragment-->onCreateView()")
        mBinding = initViewBinding()
        mBinding.root.hideStatusBar()
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logD(TAG, "BaseFragment-->onViewCreated()")
    }

    override fun onStart() {
        super.onStart()
        logD(TAG, "BaseFragment-->onStart()")
    }

    override fun onResume() {
        super.onResume()
        logD(TAG, "BaseFragment-->onResume()")

        //当Fragment在屏幕上可见并且没有加载过数据时调用
        if (!mHasLoadedData) {
            loadDataOnce()
            logD(TAG, "BaseFragment-->loadDataOnce()")
            mHasLoadedData = true
        }
    }

    /**
     * 页面首次可见时调用一次该方法，在这里可以请求网络数据等。
     */
    protected abstract fun loadDataOnce()



}