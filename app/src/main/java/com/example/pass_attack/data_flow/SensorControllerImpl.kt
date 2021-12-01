package com.example.pass_attack.data_flow

import android.hardware.Sensor
import android.hardware.SensorManager
import com.example.pass_attack.MEASUREMENT_DELAY_IN_MILLISECONDS
import com.example.pass_attack.utils.RxSensorManager
import com.example.pass_attack.utils.applySensorSchedulers
import com.example.pass_attack.utils.averagingSensorValue
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit

class SensorControllerImpl(
    private var rxSensorManager: RxSensorManager,
    private var appDataSource: DataFlowContract.AppDataSource
) : DataFlowContract.SensorController {

    lateinit var rawDataHandler: DataFlowContract.RawDataHandler

    private val compositeDisposable = CompositeDisposable()

    override fun observeSensors() {
        rawDataHandler = RawDataHandlerImpl(appDataSource)

        rxSensorManager.observeSensor(Sensor.TYPE_GYROSCOPE, SensorManager.SENSOR_DELAY_FASTEST)
            .toFlowable(BackpressureStrategy.BUFFER)
            .buffer(MEASUREMENT_DELAY_IN_MILLISECONDS, TimeUnit.MILLISECONDS)
            .map { averagingSensorValue(it) }
            .filter { !it.length().isNaN() }
            .applySensorSchedulers()
            .subscribe {
                rawDataHandler.addSensorEvent(it, Sensor.TYPE_GYROSCOPE)
            }
            .let { compositeDisposable.add(it) }

        rxSensorManager.observeSensor(Sensor.TYPE_ACCELEROMETER, SensorManager.SENSOR_DELAY_FASTEST)
            .toFlowable(BackpressureStrategy.BUFFER)
            .buffer(MEASUREMENT_DELAY_IN_MILLISECONDS, TimeUnit.MILLISECONDS)
            .map { averagingSensorValue(it) }
            .filter { !it.length().isNaN() }
            .applySensorSchedulers()
            .subscribe {
                rawDataHandler.addSensorEvent(it, Sensor.TYPE_ACCELEROMETER)
            }
            .let { compositeDisposable.add(it) }
    }

    override fun stopObservingSensors() {
        compositeDisposable.clear()
    }
}