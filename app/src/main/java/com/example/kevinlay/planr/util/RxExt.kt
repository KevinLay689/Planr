package com.example.kevinlay.planr.util

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

fun CompositeDisposable.into(disposable: CompositeDisposable?) {
    disposable?.add(this)
}

fun Disposable.into(disposable: CompositeDisposable?) {
    disposable?.add(this)
}