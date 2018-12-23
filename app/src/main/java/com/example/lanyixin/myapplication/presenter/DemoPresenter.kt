package com.kotlinmvp.mvp.presenter

import com.example.lanyixin.myapplication.model.DemoModel
import com.kotlinmvp.mvp.contract.DemoContract
import com.kotlinmvp.base.BasePresenter
import com.kotlinmvp.net.exception.ExceptionHandle


class DemoPresenter : BasePresenter<DemoContract.View>(), DemoContract.Presenter {

    private val mModel by lazy { DemoModel() }

    /**
     * 获取数据
     */
    override fun requestHotWordData() {

        addSubscription(disposable = mModel.requestHotWordData()
                .subscribe({ string ->
                    mRootView?.apply {
                        setHotWordData(string)
                    }
                }, { throwable ->
                    mRootView?.apply {
                        //处理异常
                        showError(ExceptionHandle.handleException(throwable),ExceptionHandle.errorCode)
                    }
                }))
    }

}