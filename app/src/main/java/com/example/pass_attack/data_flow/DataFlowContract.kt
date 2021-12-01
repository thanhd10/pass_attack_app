package com.example.pass_attack.data_flow

import com.example.pass_attack.model.MeasurementModel
import io.reactivex.rxjava3.core.Observable
import javax.vecmath.Vector3d

interface DataFlowContract {

    interface SensorController {

        fun observeSensors()

        fun stopObservingSensors()
    }

    interface RawDataHandler {

        fun addSensorEvent(sensorEvent: Vector3d, sensorType: Int)
    }

    interface AppDataSource {

        fun addMeasurement(measurementModel: MeasurementModel)

        fun getObservableMeasurements(): Observable<MeasurementModel>

        fun getFinalMeasurements(): List<MeasurementModel>

        fun clearMeasurements()
    }
}