package com.example.pass_attack.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pass_attack.R
import com.example.pass_attack.model.MeasurementModel
import com.example.pass_attack.utils.setFormattedMeasurementValue
import com.example.pass_attack.utils.setFormattedTimeValue
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.measurement_data_view.*

class MainActivity : AppCompatActivity(), MainContract.View {

    lateinit var presenter: MainContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = MainPresenter()
        presenter.setView(this)
        presenter.initDataFlow(applicationContext)
    }

    override fun onStart() {
        super.onStart()

        startRecordButton.setOnClickListener {
            startRecord()
        }
        stopRecordButton.setOnClickListener {
            stopRecord()
        }
        sendRecordButton.setOnClickListener {
            sendRecord()
        }
    }

    override fun onStop() {
        super.onStop()
        startRecordButton.setOnClickListener(null)
        stopRecordButton.setOnClickListener(null)
    }

    override fun startRecord() {
        presenter.onStartRecording()
    }

    override fun stopRecord() {
        presenter.onStopRecording()
    }

    override fun sendRecord() {
        presenter.onSendRecord(applicationContext)
        sendToast(getString(R.string.start_send_message))
    }

    override fun updateMeasurementView(measurementModel: MeasurementModel) {
        measurementModel.let {
            currentTimeText.setFormattedTimeValue(
                measurementModel.timeDifference
            )
            accXText.setFormattedMeasurementValue(
                R.string.measurement_accX, measurementModel.accX
            )
            accYText.setFormattedMeasurementValue(
                R.string.measurement_accY, measurementModel.accY
            )
            accZText.setFormattedMeasurementValue(
                R.string.measurement_accZ, measurementModel.accZ
            )
            gyroXText.setFormattedMeasurementValue(
                R.string.measurement_gyroX, measurementModel.gyroX
            )
            gyroYText.setFormattedMeasurementValue(
                R.string.measurement_gyroY, measurementModel.gyroY
            )
            gyroZText.setFormattedMeasurementValue(
                R.string.measurement_gyroZ, measurementModel.gyroZ
            )
        }
    }

    override fun sendToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }
}