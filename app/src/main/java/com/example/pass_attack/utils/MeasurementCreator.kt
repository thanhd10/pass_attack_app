package com.example.pass_attack.utils

import com.example.pass_attack.model.MeasurementModel
import javax.vecmath.Vector3d

fun createMeasurement(
    gyroVector: Vector3d?,
    accVector: Vector3d?,
    timestamp: Long,
    priorMeasurement: MeasurementModel?,
): MeasurementModel {
    return MeasurementModel(
        accX = accVector?.x ?: 0.0,
        accY = accVector?.y ?: 0.0,
        accZ = accVector?.z ?: 0.0,
        gyroX = gyroVector?.x ?: 0.0,
        gyroY = gyroVector?.y ?: 0.0,
        gyroZ = gyroVector?.z ?: 0.0,
        timestamp = timestamp,
        timeDifference = getTimeDifference(timestamp, priorMeasurement),
    )
}

// Method returns time since record-start in seconds.
private fun getTimeDifference(newTimestamp: Long, priorMeasurement: MeasurementModel?): Double {
    if (priorMeasurement == null)
        return 0.0

    return priorMeasurement.timeDifference +
            getTimestampDifference(newTimestamp, priorMeasurement.timestamp)
}

private fun getTimestampDifference(currentTimestamp: Long, priorTimestamp: Long): Double {
    return (currentTimestamp.toDouble() - priorTimestamp.toDouble()) / 1000.0
}