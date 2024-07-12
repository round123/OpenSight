package com.tao.opensight.ui.common

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.viewbinding.ViewBinding
import com.tao.opensight.ext.logD


abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {
    protected lateinit var mBinding: VB
    protected abstract fun initViewBinding(): VB

    /**
     * 日志输出标志
     */
    protected val TAG: String = this.javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mBinding = initViewBinding()
        setContentView(mBinding.root)
        //hideStatusBar(mBinding.root)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        logD(TAG, "BaseActivity-->onSaveInstanceState()")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        logD(TAG, "BaseActivity-->onRestoreInstanceState()")
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        logD(TAG, "BaseActivity-->onNewIntent()")
    }

    override fun onRestart() {
        super.onRestart()
        logD(TAG, "BaseActivity-->onRestart()")
    }

    override fun onStart() {
        super.onStart()
        logD(TAG, "BaseActivity-->onStart()")
    }

    override fun onResume() {
        super.onResume()
        logD(TAG, "BaseActivity-->onResume()")
    }

    override fun onPause() {
        super.onPause()
        logD(TAG, "BaseActivity-->onPause()")
    }

    override fun onStop() {
        super.onStop()
        logD(TAG, "BaseActivity-->onStop()")
    }

    private fun hideStatusBar(view: View) {
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, systemBars.top, 0, systemBars.bottom)
            WindowInsetsCompat.CONSUMED
        }
    }

}