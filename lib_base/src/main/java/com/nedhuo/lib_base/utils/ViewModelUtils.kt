package com.nedhuo.lib_base.utils

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
    }

    fun <VM : ViewModel> createViewModel(
        fragment: Fragment, factory: ViewModelProvider.Factory? = null, position: Int
    ): VM {
        val clazz = (fragment.javaClass.genericSuperclass as ParameterizedType)
            .actualTypeArguments.filterIsInstance<Class<*>>()
        val viewModel = clazz[position] as Class<VM>
        return factory?.let {
            ViewModelProvider(fragment, factory).get(viewModel)
        } ?: let {
            ViewModelProvider(fragment).get(viewModel)
        }
    }


    //存在的必要时什么
    fun <VM : ViewModel> createActViewModel(
        fragment: Fragment,
        factory: ViewModelProvider.Factory? = null,
        position: Int
    ): VM {
        val clazz =
            (fragment.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments.filterIsInstance<Class<*>>()
        val viewModel = clazz[position] as Class<VM>
        return factory?.let {
            ViewModelProvider(
                fragment.requireActivity(),
                factory
            ).get(viewModel)
        } ?: let {
            ViewModelProvider(fragment.requireActivity()).get(viewModel)
        }
    }
}