package com.maxmesh.dictionary.data.storage.room

import com.maxmesh.dictionary.domain.entity.DataModel
import com.maxmesh.dictionary.data.datasource.DataSource
import io.reactivex.Observable

class RoomDatabaseImpl : DataSource<List<DataModel>> {
    override fun getData(word: String): Observable<List<DataModel>> {
        TODO("need implemented")
    }
}