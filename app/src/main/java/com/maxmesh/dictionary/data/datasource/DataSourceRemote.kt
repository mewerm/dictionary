package com.maxmesh.dictionary.data.datasource

import com.maxmesh.dictionary.domain.entity.DataModel
import com.maxmesh.dictionary.data.network.retrofit.RetrofitImpl
import io.reactivex.Observable

class DataSourceRemote(private val remoteProvider: RetrofitImpl = RetrofitImpl()) :
    DataSource<List<DataModel>> {

    override fun getData(word: String): Observable<List<DataModel>> = remoteProvider.getData(word)
}