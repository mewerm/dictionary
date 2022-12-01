package com.maxmesh.dictionary.ui.presenter

import com.maxmesh.dictionary.domain.AppState
import com.maxmesh.dictionary.ui.base.BaseView

interface Presenter<T : AppState, V : BaseView> {
    fun attachView(view: V)

    fun detachView(view: V)

    fun getData(word: String, isOnline: Boolean)
}