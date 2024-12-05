package com.hannhb.myapplication.repository

import android.util.Log
import com.hannhb.myapplication.data.DataOrException
import com.hannhb.myapplication.model.QuestionItem
import com.hannhb.myapplication.network.QuestionApi
import javax.inject.Inject
import javax.inject.Singleton

class QuestionRepository@Inject constructor(
    private val api: QuestionApi
){
    private val dataOrException = DataOrException<ArrayList<QuestionItem>, Boolean, Exception>()

    suspend fun getAllQuestions(): DataOrException<ArrayList<QuestionItem>, Boolean, Exception> {
        try {
            dataOrException.loading = true
            dataOrException.data = api.getAllQuestion()
            if (dataOrException.data.toString().isNotEmpty()){
                dataOrException.loading = false

            }

        } catch(e: Exception) {
            dataOrException.e = e
            Log.d("trivia", "getAllQuestion: ${dataOrException.e?.localizedMessage}")
        }
        return dataOrException
    }

}