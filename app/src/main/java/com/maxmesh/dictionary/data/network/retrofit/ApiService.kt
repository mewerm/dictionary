package com.maxmesh.dictionary.data.network.retrofit

import com.maxmesh.dictionary.domain.entity.DataModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("words/search")
    fun search(@Query("search") wordToSearch: String): Observable<List<DataModel>>
}