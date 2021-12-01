package com.example.pass_attack.network.model


data class PassAttackRecordDto(
    val smartphone: String,
    val count_measurements: Int,
    val frequency: Int,
    val measurements: List<MeasurementDto>
)

data class MeasurementDto(
    val acc_x: Double,
    val acc_y: Double,
    val acc_z: Double,
    val gyr_x: Double,
    val gyr_y: Double,
    val gyr_z: Double,
    val timestamp: Long,
    val time_difference: Double
)