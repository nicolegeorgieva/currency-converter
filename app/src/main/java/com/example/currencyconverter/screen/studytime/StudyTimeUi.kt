package com.example.currencyconverter.screen.studytime

data class StudyTimeUi(
    val startHour: String,
    val startMins: String,
    val endHour: String,
    val endMins: String,
    val cutMins: String,
    val totalTimeOfStudying: String,
    val errorOccured: Boolean
)
