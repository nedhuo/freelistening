package com.nedhuo.lib_base

import androidx.lifecycle.ViewModel
import com.kunminx.architecture.ui.callback.UnPeekLiveData


open class BaseViewModel :ViewModel(){
    //相比于定义一个内部类实例化 区别是什么
    val showLoading by lazy { UnPeekLiveData<Boolean>() }

//    inner class UiLoadingChange{
//        val showLoading by lazy {
//            UnPeekLiveData<Boolean>()
//        }
//    }
}