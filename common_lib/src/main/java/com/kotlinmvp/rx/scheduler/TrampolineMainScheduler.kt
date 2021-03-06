package com.kotlinmvp.rx.scheduler

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 *    .
 * desc:
 */


class TrampolineMainScheduler<T> private constructor() : BaseScheduler<T>(Schedulers.trampoline(), AndroidSchedulers.mainThread())
