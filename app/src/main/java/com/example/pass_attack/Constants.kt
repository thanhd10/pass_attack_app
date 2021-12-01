package com.example.pass_attack


const val DATA_FLOW = "DataFlow"


// set frequency of sensor data collection
const val FREQUENCY_IN_HERTZ = 100

// helper value to define the delay of a measurement
const val MEASUREMENT_DELAY_IN_MILLISECONDS: Long = (1000 / FREQUENCY_IN_HERTZ).toLong()

