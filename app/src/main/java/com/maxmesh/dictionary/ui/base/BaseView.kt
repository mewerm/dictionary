package com.maxmesh.dictionary.ui.base

import com.maxmesh.dictionary.domain.AppState

interface BaseView {
    fun renderData(appState: AppState)
}




