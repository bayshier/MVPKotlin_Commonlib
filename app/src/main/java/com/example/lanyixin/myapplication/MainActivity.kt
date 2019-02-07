package com.example.lanyixin.myapplication

import android.content.Intent
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import com.example.lanyixin.myapplication.api.ResultBase
import com.example.lanyixin.myapplication.format.CFUtils
import com.kotlinmvp.mvp.contract.DemoContract
import com.kotlinmvp.base.BaseActivity
import com.kotlinmvp.mvp.presenter.DemoPresenter
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.StringBuilder
import java.util.*
import java.util.regex.Pattern

class MainActivity : BaseActivity(), DemoContract.View {

    override fun initData() {
        mPresenter.requestHotWordData();
    }

    override fun initView() {

        title = "一套完整的数字货币与法币汇率转换以及货币符号匹配展示规则demo"

        val res = this.getResources()
        val config = res.getConfiguration()
        var locale = res.getConfiguration().locale
        locale = Locale.SIMPLIFIED_CHINESE
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config.setLocale(locale)
        } else {
            config.locale = locale
        }
        this
            .getResources()
            .updateConfiguration(
                config,
                this.getResources().getDisplayMetrics()
            )

        switch1.setOnCheckedChangeListener { buttonView, isChecked ->
            val res = this.getResources()
            val config = res.getConfiguration()
            var locale = res.getConfiguration().locale

            if (isChecked) {
                switch1.text = Locale.ENGLISH.language
                locale = Locale.ENGLISH
            } else {
                switch1.text = Locale.SIMPLIFIED_CHINESE.language
                locale = Locale.SIMPLIFIED_CHINESE
            }

            Log.i("df", locale.getCountry() + "    " + locale.getLanguage())

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                config.setLocale(locale)
            } else {
                config.locale = locale
            }

            this
                .getResources()
                .updateConfiguration(
                    config,
                    this.getResources().getDisplayMetrics()
                )
        }

        floatingActionButton.setOnClickListener {
            val intent = Intent()
            intent.setClass(this@MainActivity, TxtActivity::class.java!!)
            startActivity(intent)
        }

        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                val str = s.toString()

                if (str.isEmpty()) return
                if (str.length > 30) return
                if (!isNumeric(str)) return

                val vol = java.lang.Double.valueOf(str)
                num.text = str

                val builder = CFUtils.Builder(application)

                //法币
                fb1.text = builder.setnumber(vol)
                    .setlega(true)
                    .setword(true)
                    .setformat(true)
                    .build()
                    .cfRules(application)
                fb2.text = builder.setnumber(vol)
                    .setlega(true)
                    .setword(true)
                    .setformat(false)
                    .build()
                    .cfRules(application)
                fb3.text = builder.setnumber(vol)
                    .setlega(true)
                    .setword(false)
                    .setformat(true)
                    .build()
                    .cfRules(application)
                fb4.text = builder.setnumber(vol)
                    .setlega(true)
                    .setword(false)
                    .setformat(false)
                    .build()
                    .cfRules(application)

                //虚拟货币
                xn1.text = builder.setnumber(vol)
                    .setlega(false)
                    .setword(true)
                    .setformat(true)
                    .build()
                    .cfRules(application)
                xn2.text = builder.setnumber(vol)
                    .setlega(false)
                    .setword(true)
                    .setformat(false)
                    .build()
                    .cfRules(application)
                xn3.text = builder.setnumber(vol)
                    .setlega(false)
                    .setword(false)
                    .setformat(true)
                    .build()
                    .cfRules(application)
                xn4.text = builder.setnumber(vol)
                    .setlega(false)
                    .setword(false)
                    .setformat(false)
                    .build()
                    .cfRules(application)

                xn5.text = CFUtils.percentage(vol * 100)
            }
        })

    }

    //判断是否是数字
    fun isNumeric(str: String): Boolean {
        //Pattern pattern = Pattern.compile("-?[0-9]+.?[0-9]+");//这个有问题，一位的整数不能通过
        val pattern = Pattern.compile("^(\\-|\\+)?\\d+(\\.\\d+)?$")//这个是对的
        val isNum = pattern.matcher(str)
        return isNum.matches()
    }

    override fun start() {
    }

    override fun showError(errorMsg: String, errorCode: Int) {
    }

    override fun setHotWordData(string: ResultBase<List<String>>) {

        val stringBuffer = StringBuilder()

        for (item in string.data) {
            println(item)
            stringBuffer.append(item + " ")
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
