package com.maxmesh.dictionary.data.repository

import com.maxmesh.dictionary.domain.entity.DataModel
import com.maxmesh.dictionary.data.datasource.DataSource
import io.reactivex.Observable

class RepositoryImpl(private val dataSource: DataSource<List<DataModel>>) :
    Repository<List<DataModel>> {

    override fun getData(word: String): Observable<List<DataModel>> = dataSource.getData(word)
}