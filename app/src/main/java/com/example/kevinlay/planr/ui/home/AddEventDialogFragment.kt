package com.example.kevinlay.planr.ui.home

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.kevinlay.planr.R
import com.example.kevinlay.planr.repository.model.Event
import java.util.*

class AddEventDialogFragment: DialogFragment() {

    private lateinit var eventName: EditText
    private lateinit var eventDateStart: EditText
    private lateinit var eventDateEnd: EditText
    private lateinit var eventInvites: EditText
    private lateinit var eventCost: EditText
    private lateinit var addEvent: Button
    private lateinit var cancel: Button

    //TODO: this should not be set after created, find another way
    lateinit var mListener: EventDialogListener

    interface EventDialogListener {
        fun onDialogPositiveClick(event: Event)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.create_event, container, false)

        eventName = view.findViewById(R.id.eventName)
        eventDateStart = view.findViewById(R.id.eventDateStart)
        eventDateEnd = view.findViewById(R.id.eventDateEnd)
        eventInvites = view.findViewById(R.id.eventInvites)
        eventCost = view.findViewById(R.id.eventCost)

        addEvent = view.findViewById(R.id.addEventButton)
        addEvent.setOnClickListener { _ ->
                val event = Event(UUID.randomUUID().toString(),
                        "",
                        eventName.text.toString(),
                        eventCost.text.toString().toFloat(),
                        "")
            mListener.onDialogPositiveClick(event)
            dismiss()
        }

        cancel = view.findViewById(R.id.cancelButton)
        cancel.setOnClickListener{ _ ->
            dismiss()
        }
        return view
    }


    override fun onResume() {
        super.onResume()
        val params = dialog.window!!.attributes
        params.width = ViewGroup.LayoutParams.MATCH_PARENT
        dialog.window!!.attributes = params as android.view.WindowManager.LayoutParams
    }
}