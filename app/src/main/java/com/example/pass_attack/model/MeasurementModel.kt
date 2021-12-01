package com.example.pass_attack.model

data class MeasurementModel(
    val accX: Double,
    val accY: Double,
    val accZ: Double,
    val gyroX: Double,
    val gyroY: Double,
    val gyroZ: Double,
    val timestamp: Long,
    val timeDifference: Double
) {
    override fun toString(): String {
        val stringBuilder: StringBuilder = StringBuilder()

        stringBuilder.append("accX=$accX, ")
        stringBuilder.append("accY=$accY, ")
        stringBuilder.append("accZ=$accZ, ")
        stringBuilder.append("gyroX=$gyroX, ")
        stringBuilder.append("gyroY=$gyroY, ")
        stringBuilder.append("gyroZ=$gyroZ, ")
        stringBuilder.append("timestamp=$timestamp, ")
        stringBuilder.append("timedifference=$timeDifference")
        return stringBuilder.toString()
    }
}