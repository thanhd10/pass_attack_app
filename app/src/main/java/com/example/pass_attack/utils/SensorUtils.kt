package com.example.pass_attack.utils

import android.hardware.SensorEvent
import javax.vecmath.Vector3d

fun averagingSensorValue(list: List<SensorEvent>): Vector3d {
    val avgX = list.map { it.values[0] }.average()
    val avgY = list.map { it.values[1] }.average()
    val avgZ = list.map { it.values[2] }.average()
    return Vector3d(avgX, avgY, avgZ)
}