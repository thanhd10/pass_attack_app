package com.example.pass_attack.utils

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import io.reactivex.rxjava3.core.Observable

/**
 * Inspired by https://github.com/nvanbenschoten/RxSensor/blob/master/rxsensor/src/main/java/com/nvanbenschoten/rxsensor/RxSensorManager.java
 * Extracted to support rxjava3 Observable
 */
class RxSensorManager(private val sensorManager: SensorManager) {

    /**
     * emit Observable<SensorEvent> based on the sensorType that is desired to be retrieved
     * @param sensorType: a type of [Sensor] that should be observed
     * @param samplingPeriodUs: a Sensor_Delay defined from [SensorManager]
     * @return an observable SensorEvent
     */
    fun observeSensor(sensorType: Int, samplingPeriodUs: Int): Observable<SensorEvent> {
        return observeSensor(sensorType, samplingPeriodUs, 0)
    }

    @Suppress("MemberVisibilityCanBePrivate")
    fun observeSensor(
        sensorType: Int,
        samplingPeriodUs: Int,
        maxReportLatencyUs: Int
    ): Observable<SensorEvent> {
        return Observable.create { subscriber ->

            val sensor = sensorManager.getDefaultSensor(sensorType)
            if (sensor == null) {
                subscriber.onError(Exception("Invalid sensor type"))
            }

            val listener: SensorEventListener = object : SensorEventListener {
                override fun onSensorChanged(event: SensorEvent) {
                    subscriber.onNext(event)
                }

                override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
            }

            val success: Boolean = sensorManager.registerListener(
                listener,
                sensor,
                samplingPeriodUs,
                maxReportLatencyUs
            )
            Log.d("RxSensorManager", "Registered Listener=$sensorType")

            if (!success) {
                subscriber.onError(Exception("Failed to register Sensor Listener"))
            }

            subscriber.setCancellable {
                Log.d("RxSensorManager", "Unregistered Listener=$sensorType successfully!")
                sensorManager.unregisterListener(listener)
            }
        }
    }
}