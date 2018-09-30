package com.example.kevinlay.planr.ui.trip

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import com.example.kevinlay.planr.MainActivity
import com.example.kevinlay.planr.PlanrApplication
import com.example.kevinlay.planr.R
import com.example.kevinlay.planr.repository.PlanRepository
import com.example.kevinlay.planr.repository.model.Event
import com.example.kevinlay.planr.repository.model.Trip
import com.example.kevinlay.planr.ui.event.AddEventDialogFragment
import com.example.kevinlay.planr.ui.event.EventsAdapter
import com.example.kevinlay.planr.util.into
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class CreateTripFragment: Fragment(), AddEventDialogFragment.EventDialogListener {

    private lateinit var addEventButton: Button
    private lateinit var eventRecyclerView: RecyclerView
    private lateinit var eventAdapter: EventsAdapter
    private lateinit var createTripButton: Button
    private lateinit var tripName: EditText
    private lateinit var privateCheckBox: CheckBox

    private val events: ArrayList<Event> = ArrayList()

    @Inject lateinit var planRepository: PlanRepository

    private val disposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val planrApplication = context?.applicationContext as PlanrApplication

        planrApplication.appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_create_trip, container, false)

        val mainActivity = activity
        if (mainActivity is MainActivity) {
            mainActivity.hideFab()
        }

        eventAdapter = EventsAdapter(events)

        addEventButton = view.findViewById(R.id.addEvent)
        eventRecyclerView = view.findViewById(R.id.eventList)
        createTripButton = view.findViewById(R.id.createTrip)
        tripName = view.findViewById(R.id.tripName)
        privateCheckBox = view.findViewById(R.id.privateCheckBox)

        eventRecyclerView.let {
            it.layoutManager = LinearLayoutManager(activity?.baseContext)
            it.adapter = eventAdapter
        }

        addEventButton.setOnClickListener { _ ->
            AddEventDialogFragment().also {
                it.mListener = this
                it.show(fragmentManager, "")
            }
        }

        createTripButton.setOnClickListener { _ ->
            saveTripAndEvents()
        }

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }

    override fun onDialogPositiveClick(event: Event) {
        events.add(event)
        eventAdapter.notifyItemInserted(events.size)
    }


    private fun saveTripAndEvents() {
        val trip = Trip(tripId = UUID.randomUUID().toString(),
                tripName = tripName.text.toString(),
                isPrivate = privateCheckBox.isChecked,
                eventList = events,
                ownerId = planRepository.remoteDataSource.firebaseAuth.uid ?: "null")

        planRepository.saveTrip(trip)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe (Toast.makeText(activity, "Trip Saved!", Toast.LENGTH_LONG)::show)
                           {Toast.makeText(activity, "Error: $it", Toast.LENGTH_LONG).show()}
                .into(disposable)

        trip.eventList?.forEach { event ->
            planRepository.saveEvent(event)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
                    .into(disposable)
        }
    }
}