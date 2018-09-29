package com.example.kevinlay.planr.ui.browse

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kevinlay.planr.PlanrApplication
import com.example.kevinlay.planr.R
import com.example.kevinlay.planr.repository.PlanRepository
import com.example.kevinlay.planr.util.into
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class BrowseFragment: Fragment() {

    private lateinit var nearMeRecyclerView: RecyclerView
    private lateinit var trendingRecyclerView: RecyclerView
    private lateinit var freindsRecyclerView: RecyclerView

    private lateinit var browseAdapter: BrowseAdapter

    private val disposable: CompositeDisposable = CompositeDisposable()

    @Inject lateinit var planRepository: PlanRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val planrApplication = context?.applicationContext as PlanrApplication

        planrApplication.appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_browse, container, false)

        nearMeRecyclerView = view.findViewById(R.id.nearMeReyclerView)
        trendingRecyclerView = view.findViewById(R.id.trendingRecyclerView)
        freindsRecyclerView = view.findViewById(R.id.freindsRecyclerView)

        browseAdapter = BrowseAdapter(arrayListOf())

        planRepository.getTrips("")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({ trips ->
                    browseAdapter.trips = trips
                    browseAdapter.notifyDataSetChanged()
                }) { error -> Log.i(TAG, "$error")}
                .into(disposable)

        nearMeRecyclerView.also {
            it.layoutManager = LinearLayoutManager(this.context,
                    LinearLayoutManager.HORIZONTAL,
                    false)
            it.adapter = browseAdapter
        }

        trendingRecyclerView.also {
            it.layoutManager = LinearLayoutManager(this.context,
                    LinearLayoutManager.HORIZONTAL,
                    false)
            it.adapter = browseAdapter
        }

        freindsRecyclerView.also {
            it.layoutManager = LinearLayoutManager(this.context,
                    LinearLayoutManager.HORIZONTAL,
                    false)
            it.adapter = browseAdapter
        }


        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }

    companion object {
        const val TAG = "BrowseFragment"
    }
}