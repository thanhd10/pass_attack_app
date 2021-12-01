package com.example.pass_attack.data_flow

import android.hardware.Sensor
import android.util.Log
import com.example.pass_attack.DATA_FLOW
import com.example.pass_attack.model.MeasurementModel
import com.example.pass_attack.utils.createMeasurement
import javax.vecmath.Vector3d

class RawDataHandlerImpl(
    private val appDataSource: DataFlowContract.AppDataSource
) : DataFlowContract.RawDataHandler {

    private var currentGyroAxisVector: Vector3d? = null
    private var currentAccAxisVector: Vector3d? = null
    private var priorMeasurementEvent: MeasurementModel? = null

    // boolean flags needed to continue processing only if both events are received
    private var accEventReceived: Boolean = false
    private var gyroEventReceived: Boolean = false

    override fun addSensorEvent(sensorEvent: Vector3d, sensorType: Int) {

        when (sensorType) {
            Sensor.TYPE_GYROSCOPE -> {
                currentGyroAxisVector = sensorEvent
                gyroEventReceived = true
            }
            Sensor.TYPE_ACCELEROMETER -> {
                currentAccAxisVector = sensorEvent
                accEventReceived = true
            }
            else -> throw Exception("Invalid Sensor Type Received!")
        }

        // only add measurement, if both new accelerometer and gyroscope events are retrieved
        if (accEventReceived && gyroEventReceived) {
            val measurementEvent = createMeasurement(
                gyroVector = currentGyroAxisVector,
                accVector = currentAccAxisVector,
                timestamp = System.currentTimeMillis(),
                priorMeasurement = priorMeasurementEvent
            )
            Log.d(DATA_FLOW, "Created measurement with $measurementEvent")

            priorMeasurementEvent = measurementEvent
            appDataSource.addMeasurement(measurementEvent)

            accEventReceived = false
            gyroEventReceived = false
        }
    }
}