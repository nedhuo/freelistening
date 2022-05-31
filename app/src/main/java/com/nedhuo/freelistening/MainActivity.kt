package com.nedhuo.freelistening

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.launcher.ARouter
import com.nedhuo.freelistening.databinding.ActivityMainBinding
import com.nedhuo.lib_base.BaseActivity
import com.nedhuo.lib_base.BaseViewModel
import com.nedhuo.lib_common.constants.SIX_MONTH_FRAGMENT_HOME

class MainActivity : BaseActivity<ActivityMainBinding, BaseViewModel>() {
    override fun initView() {


    }

    override fun initListener() {

    }

    override fun initData() {
//        val beginTransaction = supportFragmentManager.beginTransaction()
//        beginTransaction.replace(
//            R.id.fl_replace,
//            (ARouter.getInstance().build(SIX_MONTH_FRAGMENT_HOME).navigation()) as Fragment
//        ).commit()

        val beginTransaction = supportFragmentManager.beginTransaction()
        val fragment = ARouter.getInstance().build(SIX_MONTH_FRAGMENT_HOME).navigation()
        beginTransaction.add(mBinding.flReplace.id, fragment as Fragment)
        beginTransaction.commitNowAllowingStateLoss()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

}