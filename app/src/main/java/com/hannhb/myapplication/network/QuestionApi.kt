package com.hannhb.myapplication.network

import com.hannhb.myapplication.model.Question
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface QuestionApi {
    @GET("world.json")
    suspend fun getAllQuestion(): Question
}