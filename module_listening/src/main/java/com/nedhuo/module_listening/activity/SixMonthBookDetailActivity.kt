package com.nedhuo.module_listening.activity

import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.nedhuo.lib_base.BaseActivity
import com.nedhuo.lib_base.BaseViewModel
import com.nedhuo.lib_common.constants.SIX_MONTH_ACTIVITY_BOOK_DETAIL
import com.nedhuo.module_listening.R
import com.nedhuo.module_listening.databinding.ListeningActivitySixMonthBookDetailBinding
import com.nedhuo.module_listening.viewmodel.SixMonthViewModel

@Route(path = SIX_MONTH_ACTIVITY_BOOK_DETAIL)
class SixMonthBookDetailActivity :
    BaseActivity<ListeningActivitySixMonthBookDetailBinding, SixMonthViewModel>() {

    @Autowired
    var bookSuffix: String? = null

    override fun getLayoutId(): Int {
        return R.layout.listening_activity_six_month_book_detail
    }

    override fun initView() {

    }

    override fun initListener() {

    }

    override fun initData() {
        mViewModel.getBookDetail(bookSuffix)
    }
}