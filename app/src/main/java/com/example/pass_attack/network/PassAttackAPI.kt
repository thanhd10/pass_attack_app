package com.example.pass_attack.network

import com.example.pass_attack.network.model.BasicResponseDto
import com.example.pass_attack.network.model.PassAttackRecordDto
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface PassAttackAPI {

    // TODO SET CORRECT POST END POINT
    @POST("/pass_attack_record")
    fun postRecord(@Body passAttackRecordDto: PassAttackRecordDto): Single<BasicResponseDto>
}