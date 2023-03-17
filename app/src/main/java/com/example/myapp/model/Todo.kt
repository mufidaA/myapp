package com.example.myapp.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


const val BASE_URL = "https://api.ouka.fi/v1/"

interface TodosApi {
    @GET("city_personnel_kpi")
    suspend fun getTodos(): List<Todo>
    companion object {
        var todosService : TodosApi? = null
        fun getInstance() : TodosApi {
            if (todosService === null) {
                todosService = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(TodosApi::class.java)
            }
            return todosService!!
        }
    }
}

data class Todo(
    var year: String,
    var month: String,
    var personnel_amount: String,
    var personnel_cost:String,
    var personnel_effectivity: String,
    var personnel_sickleave_pct: String,
    var organisation: String,
    var organisation_code: String,
    var keyfield: String,
    )
