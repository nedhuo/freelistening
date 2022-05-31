package com.nedhuo.lib_base

import android.app.Activity
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.BarUtils
import com.nedhuo.lib_base.utils.ViewModelUtils
import me.jessyan.autosize.AutoSizeCompat
import java.lang.reflect.ParameterizedType

abstract class BaseActivity<VDB : ViewDataBinding, VM : BaseViewModel>(
    private val factory: ViewModelProvider.Factory? = null
) : AppCompatActivity() {

    protected lateinit var mViewModel: VM
    protected lateinit var mBinding: VDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ARouter.getInstance().inject(this)
        mViewModel = ViewModelUtils.createViewModel<VM>(this, factory, 1)
        mBinding= DataBindingUtil.setContentView(this, getLayoutId())
        BarUtils.setStatusBarLightMode(window, true)
        BarUtils.transparentStatusBar(this)
        initView()
        initListener()
        initData()
    }

    abstract fun getLayoutId(): Int

    abstract fun initView()

    abstract fun initListener()

    abstract fun initData()

    fun registerLoadingUi() {
        mViewModel.showLoading.observe(this) {
            if (it) showLoading() else disLoading()
        }
    }

    /**
     * 开启Loading
     */
    private fun showLoading(message: String = "请求网络中") {
    }

    /**
     * 关闭Loading
     */
    private fun disLoading() {
    }


    private fun <VDB : ViewBinding> Activity.getViewBinding(
        inflater: LayoutInflater,
        position: Int = 0
    ): VDB {
        val clazz =
            (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments.filterIsInstance<Class<*>>()
        val inflate = clazz[position].getDeclaredMethod("inflate", LayoutInflater::class.java)
        return inflate.invoke(null, inflater) as VDB
    }

    override fun getResources(): Resources? {
        runOnUiThread { AutoSizeCompat.autoConvertDensityBaseOnWidth(super.getResources(), 375f) }
        return super.getResources()
    }

}