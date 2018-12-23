package com.example.lanyixin.myapplication

import com.kotlinmvp.mvp.contract.DemoContract
import com.kotlinmvp.base.BaseActivity
import com.kotlinmvp.mvp.presenter.DemoPresenter
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.StringBuilder

class MainActivity : BaseActivity(), DemoContract.View {

    override fun initData() {
        mPresenter.requestHotWordData();
    }

    override fun initView() {
    }

    override fun start() {
    }

    override fun showError(errorMsg: String, errorCode: Int) {
    }

    override fun setHotWordData(string: ArrayList<String>) {

        val stringBuffer = StringBuilder()

        for (item in string) {
            println(item)
            stringBuffer.append(item)
        }
        test.text = stringBuffer.toString()
    }

    override fun showLoading() {
    }

    override fun dismissLoading() {
    }

    override fun layoutId(): Int = R.layout.activity_main

    private val mPresenter by lazy { DemoPresenter() }

    init {
        mPresenter.attachView(this)
    }

}
