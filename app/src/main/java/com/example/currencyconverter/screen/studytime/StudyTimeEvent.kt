package com.example.currencyconverter.screen.studytime

sealed interface StudyTimeEvent {
    object OnStart : StudyTimeEvent
    data class EditStartHour(val newHour: String) : StudyTimeEvent
    data class EditStartMins(val newMins: String) : StudyTimeEvent
    data class EditEndHour(val newHour: String) : StudyTimeEvent
    data class EditEndMins(val newMins: String) : StudyTimeEvent
    data class EditCutMins(val newCutMins: String) : StudyTimeEvent
    object AddCurrentStudyToTotal : StudyTimeEvent
    object TotalTimeReset : StudyTimeEvent
}