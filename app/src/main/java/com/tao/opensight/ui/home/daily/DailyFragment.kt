package com.tao.opensight.ui.home.daily

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.tao.opensight.databinding.FragmentDailyBinding

import com.tao.opensight.ui.common.BaseFragment
import kotlinx.coroutines.launch

class DailyFragment : BaseFragment<FragmentDailyBinding>() {

    override val TAG = this::class.java.simpleName

    private lateinit var madapter: DailyAdapter

    private val  context by lazy { requireActivity() }

    private val viewModel by viewModels<DailyViewmodel>()
    override fun initViewBinding(): FragmentDailyBinding {
        return FragmentDailyBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun loadDataOnce() {
        madapter = DailyAdapter()
        mBinding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter=madapter
            setHasFixedSize(true)
            itemAnimator = null
        }

        lifecycleScope.launch {
            viewModel.getPagingData().collect {
                madapter.submitData(it)
            }
        }
    }
    companion object {
        fun newInstance() = DailyFragment()
    }
}