package com.example.currencyconverter.domain.studytime

/*
Gets the input from the user, transforms it into mins and subtracts.
Returns the result (current study time in mins).
 */
fun currentStudyMins(
    startHour: String, //10
    startMin: String, // 10
    endHour: String,
    endMin: String,
    cutTime: String
): Int {
    val startTimeMins = startHour.toInt() * 60 + startMin.toInt()
    val endTimeMins = endHour.toInt() * 60 + endMin.toInt()

    return endTimeMins - startTimeMins - cutTime.toInt()
}

/*
Adds the "currentStudy Time in mins" to the "Existing Study Time in mins".
Returns the result (total study time in mins).
 */
fun totalStudyTimeMins(currentStudyTimeMins: Int, existingStudyTimeMins: Int): Int {
    return currentStudyTimeMins + existingStudyTimeMins
}

/*
Transforms the total time in mins to the readable format - e.g 60m to 1h 00m.
 */
fun totalStudyTimeFormatted(totalStudyTimeMins: Int): String {
    val hour: Double = (totalStudyTimeMins / 60).toDouble() // 150 / 60 = 2.5
    val hourPart = hour.toString().split(".")[0] // "2"
    val minPart = hour.toString().split(".")[1] // "5"
    val mins = 0.0 + "0.$minPart".toDouble() * 60

    return "${hourPart}h ${mins}m"
}