package com.nedhuo.lib_common

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.LogUtils
import me.jessyan.autosize.AutoSizeConfig

open class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initAutoSize()
        initARouter()
        initLog()
        initThirdPartySdk()
//        initCoil()
//        UserManager.startHeartBeat()
//        UserManager.startSendMsg()
    }

    private fun initThirdPartySdk() {

    }

    private fun initLog() {
        LogUtils.getConfig().isLog2FileSwitch = BuildConfig.DEBUG
        LogUtils.getConfig().isLogSwitch = BuildConfig.DEBUG
    }

    private fun initARouter() {
        if (BuildConfig.DEBUG) {
            ARouter.openDebug()
            ARouter.openLog()
        }
        ARouter.init(this)
    }

    private fun initAutoSize() {
        AutoSizeConfig.getInstance().isCustomFragment = true
        AutoSizeConfig.getInstance().isExcludeFontScale = true
    }
}