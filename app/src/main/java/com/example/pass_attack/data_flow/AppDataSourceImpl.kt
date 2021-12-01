package com.example.pass_attack.data_flow

import com.example.pass_attack.model.MeasurementModel
import com.example.pass_attack.utils.RxObservableWithStoredList
import io.reactivex.rxjava3.core.Observable

class AppDataSourceImpl : DataFlowContract.AppDataSource {

    private val observableMeasurements = RxObservableWithStoredList<MeasurementModel>()

    override fun addMeasurement(measurementModel: MeasurementModel) {
        observableMeasurements.add(measurementModel)
    }

    override fun getObservableMeasurements(): Observable<MeasurementModel> {
        return observableMeasurements.getObservable()
    }

    override fun getFinalMeasurements() = observableMeasurements.getMutableList().toList()

    override fun clearMeasurements() {
        observableMeasurements.getMutableList().clear()
    }
}