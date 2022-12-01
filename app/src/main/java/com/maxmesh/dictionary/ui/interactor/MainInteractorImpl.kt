package com.maxmesh.dictionary.ui.interactor

import com.maxmesh.dictionary.domain.AppState
import com.maxmesh.dictionary.domain.entity.DataModel
import com.maxmesh.dictionary.data.repository.Repository
import io.reactivex.Observable

class MainInteractorImpl(

    private val remoteRepo: Repository<List<DataModel>>,
    private val localRepo: Repository<List<DataModel>>
) : Interactor<AppState> {

    override fun getData(word: String, fromRemoteSource: Boolean): Observable<AppState> {
        return if (fromRemoteSource) {
            remoteRepo.getData(word).map { AppState.Success(it) }
        } else {
            localRepo.getData(word).map { AppState.Success(it) }
        }
    }
}