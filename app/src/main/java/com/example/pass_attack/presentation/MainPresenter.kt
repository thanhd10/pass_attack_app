package com.example.pass_attack.presentation

import android.content.Context
import android.hardware.SensorManager
import com.example.pass_attack.R
import com.example.pass_attack.data_flow.AppDataSourceImpl
import com.example.pass_attack.data_flow.DataFlowContract
import com.example.pass_attack.data_flow.SensorControllerImpl
import com.example.pass_attack.network.NetworkModule
import com.example.pass_attack.network.createPassAttackRecord
import com.example.pass_attack.utils.RxSensorManager
import com.example.pass_attack.utils.applySchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable

class MainPresenter : MainContract.Presenter {

    private var mainContractView: MainContract.View? = null

    private val compositeDisposable = CompositeDisposable()

    private val passAttackAPI = NetworkModule().providePassAttackAPI()

    lateinit var rxSensorManager: RxSensorManager
    lateinit var appDataSource: DataFlowContract.AppDataSource
    lateinit var sensorController: DataFlowContract.SensorController

    override fun setView(view: MainContract.View) {
        mainContractView = view
    }

    override fun initDataFlow(context: Context) {
        appDataSource = AppDataSourceImpl()
        rxSensorManager =
            RxSensorManager(context.getSystemService(Context.SENSOR_SERVICE) as SensorManager)
        sensorController = SensorControllerImpl(rxSensorManager, appDataSource)
    }

    override fun onStartRecording() {
        appDataSource.clearMeasurements()

        sensorController.observeSensors()
        startObservingMeasurements()
    }

    override fun onStopRecording() {
        compositeDisposable.clear()
        sensorController.stopObservingSensors()
    }

    override fun onSendRecord(context: Context) {
        if (appDataSource.getFinalMeasurements().isEmpty()) {
            mainContractView?.sendToast(context.getString(R.string.no_record_available))
        } else {
            val passAttackRecordDto = createPassAttackRecord(appDataSource.getFinalMeasurements())
            passAttackAPI.postRecord(passAttackRecordDto)
                .applySchedulers()
                .subscribe({
                    mainContractView?.sendToast(context.getString(R.string.send_success))
                    appDataSource.clearMeasurements()
                }, {
                    mainContractView?.sendToast(context.getString(R.string.send_fail))
                })
        }
    }

    private fun startObservingMeasurements() {
        appDataSource.getObservableMeasurements()
            .applySchedulers()
            .subscribe { measurement ->
                mainContractView?.updateMeasurementView(measurement)
            }
            .let { compositeDisposable.add(it) }
    }
}