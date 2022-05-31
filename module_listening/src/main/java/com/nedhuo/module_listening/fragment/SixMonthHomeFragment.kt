package com.nedhuo.module_listening.fragment

import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.StringUtils
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.nedhuo.lib_base.BaseViewModel
import com.nedhuo.lib_base.utils.BaseFragment
import com.nedhuo.lib_common.bean.db.model.SixMonthBookModel
import com.nedhuo.lib_common.constants.SIX_MONTH_ACTIVITY_BOOK_DETAIL
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

        mBookAdapter?.setOnItemClickListener(object : OnItemClickListener {

            override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
                val sixMonthBookModel = adapter.getItem(position) as SixMonthBookModel
                if (StringUtils.isEmpty(sixMonthBookModel.bookSuffix)) {
                    ToastUtils.showShort("当前文件暂无法播放")
                    return
                }
                ARouter.getInstance().build(SIX_MONTH_ACTIVITY_BOOK_DETAIL)
                    .withString("bookSuffix", sixMonthBookModel.bookSuffix).navigation()
            }
        })

    }

    override fun initData() {
        //查数据库
        mViewModel.getBookList()
    }
}