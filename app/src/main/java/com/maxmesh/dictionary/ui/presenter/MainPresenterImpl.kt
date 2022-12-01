package com.maxmesh.dictionary.ui.presenter

import com.maxmesh.dictionary.domain.AppState
import com.maxmesh.dictionary.data.datasource.DataSourceLocal
import com.maxmesh.dictionary.data.datasource.DataSourceRemote
import com.maxmesh.dictionary.data.repository.RepositoryImpl
import com.maxmesh.dictionary.data.rx.SchedulerProvider
import com.maxmesh.dictionary.ui.base.BaseView
import com.maxmesh.dictionary.ui.interactor.MainInteractorImpl
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver


open class MainPresenterImpl<T : AppState, V : BaseView>(
    private val interactor: MainInteractorImpl = MainInteractorImpl(
        RepositoryImpl(DataSourceRemote()),
        RepositoryImpl(DataSourceLocal())
    ),
    protected val compositeDisposable: CompositeDisposable = CompositeDisposable(),

    private val schedulerProvider: SchedulerProvider = SchedulerProvider()
) : Presenter<T, V> {

    private var currentView: V? = null

    override fun attachView(view: V) {
        if (view != currentView) {
            currentView = view
        }
    }

    override fun detachView(view: V) {
        compositeDisposable.clear()
        if (view == currentView) {
            currentView = null
        }
    }

    override fun getData(word: String, isOnline: Boolean) {
        compositeDisposable.add(
            interactor.getData(word, isOnline)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())

                .doOnSubscribe { currentView?.renderData(AppState.Loading(null)) }
                .subscribeWith(getObserver())
        )
    }

    private fun getObserver(): DisposableObserver<AppState> {
        return object : DisposableObserver<AppState>() {
            override fun onNext(appState: AppState) {
                currentView?.renderData(appState)
            }

            override fun onError(e: Throwable) {
                currentView?.renderData(AppState.Error(e))
            }

            override fun onComplete() {
            }
        }
    }
}