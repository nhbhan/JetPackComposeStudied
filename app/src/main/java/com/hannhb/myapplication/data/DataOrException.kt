package com.hannhb.myapplication.data

class DataOrException<T, Boolean, E: Exception>(
    val data: T? = null,
    var isLoading: Boolean? = null,
    val e: E? = null
)