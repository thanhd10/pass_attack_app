package com.example.pass_attack.network

import android.os.Build
import com.example.pass_attack.FREQUENCY_IN_HERTZ
import com.example.pass_attack.model.MeasurementModel
import com.example.pass_attack.network.model.MeasurementDto
import com.example.pass_attack.network.model.PassAttackRecordDto


private fun MeasurementModel.mapToDto(): MeasurementDto {
    return MeasurementDto(
        acc_x = accX,
        acc_y = accY,
        acc_z = accZ,
        gyr_x = gyroX,
        gyr_y = gyroY,
        gyr_z = gyroZ,
        timestamp = timestamp,
        time_difference = timeDifference
    )
}

fun createPassAttackRecord(measurements: List<MeasurementModel>): PassAttackRecordDto {
    return PassAttackRecordDto(
        smartphone = Build.MODEL,
        count_measurements = measurements.size,
        frequency = FREQUENCY_IN_HERTZ,
        measurements = measurements.map { it.mapToDto() }
    )
}