package com.nedhuo.module_listening.fragment

import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.nedhuo.lib_base.utils.BaseFragment
import com.nedhuo.lib_common.constants.SIX_MONTH_FRAGMENT_HOME
import com.nedhuo.module_listening.adapter.SixMonthBookAdapter
import com.nedhuo.module_listening.databinding.ListeningFragmentSixMonthHomeBinding
import com.nedhuo.module_listening.parseUpdateFlow
import com.nedhuo.module_listening.utils.SixMonthHtmlUtils
import com.nedhuo.module_listening.viewmodel.SixMonthViewModel
import kotlinx.coroutines.flow.collect

@Route(path = SIX_MONTH_FRAGMENT_HOME)
class SixMonthHomeFragment :
    BaseFragment<ListeningFragmentSixMonthHomeBinding, SixMonthViewModel>() {
    private var mBookAdapter: SixMonthBookAdapter? = null

    override fun initView() {
        mBinding.rvBook.layoutManager = LinearLayoutManager(context)
        mBookAdapter = SixMonthBookAdapter()
        mBinding.rvBook.adapter = mBookAdapter
    }

    override fun initListener() {
        super.initListener()

        lifecycleScope.launchWhenStarted {
            parseUpdateFlow.collect {
                when (it) {
                    SixMonthHtmlUtils.SixMonthParseType.TYPE_HOME -> {
                        mViewModel.getBookList()
                    }
                }
            }
        }

        mViewModel.dataUpdate.observe(this) {
            mBookAdapter?.setNewInstance(it)
        }

    }

    override fun initData() {
        //查数据库
        mViewModel.getBookList()
    }
}