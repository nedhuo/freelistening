package com.nedhuo.lib_common.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.reflect.ParameterizedType

object ViewModelUtils {
    fun<VM:ViewModel> createViewModel(
        activity: AppCompatActivity,
        factory: ViewModelProvider.Factory? = null,
        position:Int,
    ):VM {
        //获取带有泛型的父类
        // ParameterizedType 参数化类型 https://blog.csdn.net/JustBeauty/article/details/81116144
        val clazz = (activity.javaClass.genericSuperclass as ParameterizedType)
                .actualTypeArguments.filterIsInstance<Class<*>>()
        val viewModel = clazz[position] as Class<VM>;
        return factory?.let {
            ViewModelProvider(activity, factory).get(viewModel)
        } ?: let {
            ViewModelProvider(activity).get(viewModel)
        }
//        if(factory==null){
//            ViewModelProvider(activity).get(viewModel)
//        }else{
//            ViewModelProvider(activity,factory).get(viewModel)
//        }
    }

    fun <VM : ViewModel> createViewModel(
        fragment: Fragment, position: Int, factory: ViewModelProvider.Factory? = null
    ): VM {
        val clazz =
            (fragment.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments.filterIsInstance<Class<*>>()
        val viewModel = clazz[position] as Class<VM>
        return factory?.let {
            ViewModelProvider(fragment, factory).get(viewModel)
        } ?: let {
            ViewModelProvider(fragment).get(viewModel)
        }
    }
}