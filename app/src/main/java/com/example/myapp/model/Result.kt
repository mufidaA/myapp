package com.example.myapp.model

import java.time.Month
import java.time.Year
import retrofit2.Response
import retrofit2.http.GET

interface DataApi {
    @GET("/v1/city_personnel_kpi")
    suspend fun getData() : Response<Results>
}

data class Results(
    var year: Year,
    var month: Month,
    var personnel_amount: Int,
    var personnel_cost:Int,
    var personnel_effectivity: Double,
    var personnel_sickleave_pct: Double,
    var organisation: String,
    var organisation_code: Int,
    var keyfield: Int,

    )