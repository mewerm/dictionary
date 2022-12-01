package com.maxmesh.dictionary.data.repository

import io.reactivex.Observable

interface Repository<T> {
    fun getData(word: String): Observable<T>
}