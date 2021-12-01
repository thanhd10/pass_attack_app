package com.example.pass_attack.presentation

import android.content.Context
import com.example.pass_attack.model.MeasurementModel

interface MainContract {

    interface View {

        fun startRecord()

        fun stopRecord()

        fun sendRecord()

        fun updateMeasurementView(measurementModel: MeasurementModel)

        fun sendToast(message: String)
    }

    interface Presenter {

        fun setView(view: View)

        fun initDataFlow(context: Context)

        fun onStartRecording()

        fun onStopRecording()

        fun onSendRecord(context: Context)
    }
}