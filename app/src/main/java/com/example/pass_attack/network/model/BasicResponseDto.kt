package com.example.pass_attack.network.model

import com.google.gson.annotations.SerializedName

data class BasicResponseDto(
    @SerializedName("message") val message: String?,
    @SerializedName("status") val status: String?
)

