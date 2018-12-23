package com.kotlinmvp.api

import retrofit2.http.GET
import java.util.*
import io.reactivex.Observable

interface ApiService{

    @GET("v3/queries/hot")
    fun getHotWord(): Observable<ArrayList<String>>

}