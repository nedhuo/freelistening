package com.nedhuo.lib_base.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.alibaba.android.arouter.launcher.ARouter
import com.nedhuo.lib_base.BaseViewModel
import java.lang.reflect.ParameterizedType

abstract class BaseFragment<VDB : ViewDataBinding, VM : BaseViewModel>(
    private val factory: ViewModelProvider.Factory? = null
) : Fragment() {
    protected lateinit var mBinding: VDB
    protected lateinit var mViewModel: VM


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = getViewBinding<VDB>(inflater, container)
        mBinding.lifecycleOwner = this
        mViewModel = ViewModelUtils.createViewModel(this, factory, 1)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let { initArgs(it) }
        ARouter.getInstance().inject(this)
        initView()
        initListener()
        initData()

    }

    abstract fun initView()

    open fun initListener() {}

    abstract fun initData()


    open fun initArgs(arguments: Bundle) {}

    private fun <VDB : ViewBinding> Fragment.getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        position: Int = 0
    ): VDB {
        val clazz =
            (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments.filterIsInstance<Class<*>>()
        val inflate = clazz[position].getDeclaredMethod(
            "inflate",
            LayoutInflater::class.java,
            ViewGroup::class.java,
            Boolean::class.java
        )
        return inflate.invoke(null, inflater, container, false) as VDB
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.unbind()
    }
}