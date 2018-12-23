package com.example.lanyixin.myapplication.model

import com.example.lanyixin.myapplication.api.RetrofitManager
import com.kotlinmvp.rx.scheduler.SchedulerUtils
import java.util.*
import kotlin.collections.ArrayList
import io.reactivex.Observable

class DemoModel {

    /**
     * 请求数据
     */
    fun requestHotWordData(): Observable<ArrayList<String>> {
        return RetrofitManager.service.getHotWord()
                .compose(SchedulerUtils.ioToMain())
    }

}
