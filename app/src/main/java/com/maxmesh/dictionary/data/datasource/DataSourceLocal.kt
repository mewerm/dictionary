package com.maxmesh.dictionary.data.datasource

import com.maxmesh.dictionary.domain.entity.DataModel
import com.maxmesh.dictionary.data.storage.room.RoomDatabaseImpl
import io.reactivex.Observable

class DataSourceLocal(private val remoteProvider: RoomDatabaseImpl = RoomDatabaseImpl()) :
    DataSource<List<DataModel>> {

    override fun getData(word: String): Observable<List<DataModel>> = remoteProvider.getData(word)
}