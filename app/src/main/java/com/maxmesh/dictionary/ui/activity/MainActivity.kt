package com.maxmesh.dictionary.ui.activity

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.maxmesh.dictionary.R
import com.maxmesh.dictionary.databinding.ActivityMainBinding
import com.maxmesh.dictionary.domain.AppState
import com.maxmesh.dictionary.domain.entity.DataModel
import com.maxmesh.dictionary.ui.presenter.MainPresenterImpl
import com.maxmesh.dictionary.ui.presenter.Presenter
import com.maxmesh.dictionary.ui.base.BaseActivity
import com.maxmesh.dictionary.ui.base.BaseView
import com.maxmesh.dictionary.ui.activity.adapter.MainAdapter
import com.maxmesh.dictionary.ui.search.OnSearchClickListener
import com.maxmesh.dictionary.ui.search.SearchDialogFragment
import com.maxmesh.dictionary.utils.BOTTOM_SHEET_FRAGMENT_DIALOG_TAG

class MainActivity : BaseActivity<AppState>() {

    private lateinit var binding: ActivityMainBinding
    private var adapter: MainAdapter? = null

    private val onListItemClickListener = object : MainAdapter.OnListItemClickListener {
        override fun onItemClick(data: DataModel) {
            Toast.makeText(this@MainActivity, data.text, Toast.LENGTH_SHORT).show()
        }
    }

    override fun createPresenter(): Presenter<AppState, BaseView> {
        return MainPresenterImpl()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initFabButton()
    }

    private fun initFabButton() {
        binding.searchFab.setOnClickListener {
            val searchDialogFragment = SearchDialogFragment.newInstance()
            searchDialogFragment.setOnSearchClickListener(object : OnSearchClickListener {
                override fun onClick(searchWord: String) {
                    presenter.getData(searchWord, isOnline = true)
                }
            })
            searchDialogFragment.show(supportFragmentManager, BOTTOM_SHEET_FRAGMENT_DIALOG_TAG)
        }
    }

    override fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                val data = appState.data
                if (data.isNullOrEmpty()) {
                    showViewError(getString(R.string.empty_server_response_on_success))
                } else {
                    showViewSuccess()
                    if (adapter == null) {
                        binding.mainActivityRecyclerview.apply {
                            layoutManager = LinearLayoutManager(applicationContext)
                            adapter = MainAdapter(onListItemClickListener, data)
                        }

                    } else {
                        adapter?.setData(data)
                    }
                }
            }
            is AppState.Loading -> {
                showViewLoading()
                if (appState.progress != null) {
                    binding.progressBarHorizontal.visibility = VISIBLE
                    binding.progressBarRound.visibility = GONE
                    binding.progressBarHorizontal.progress = appState.progress
                } else {
                    binding.progressBarHorizontal.visibility = GONE
                    binding.progressBarRound.visibility = VISIBLE
                }
            }
            is AppState.Error -> {
                showViewError(appState.error.message)
            }
        }
    }

    private fun showViewSuccess() {
        binding.successLinearLayout.visibility = VISIBLE
        binding.loadingFrameLayout.visibility = GONE
        binding.errorLinearLayout.visibility = GONE
    }

    private fun showViewLoading() {
        binding.successLinearLayout.visibility = GONE
        binding.loadingFrameLayout.visibility = VISIBLE
        binding.errorLinearLayout.visibility = GONE
    }

    private fun showViewError(error: String?) {
        binding.errorTextview.text = error ?: getString(R.string.undefined_error)
        binding.reloadButton.setOnClickListener {
            presenter.getData("hi", true)
        }
        binding.successLinearLayout.visibility = GONE
        binding.loadingFrameLayout.visibility = GONE
        binding.errorLinearLayout.visibility = VISIBLE
    }
}