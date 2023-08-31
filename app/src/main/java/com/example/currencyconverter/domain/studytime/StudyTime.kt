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
    cutMins: String
): Int {
    val startTimeMins = startHour.toInt() * 60 + startMin.toInt()
    val endTimeMins = endHour.toInt() * 60 + endMin.toInt()

    val res = try {
        endTimeMins - startTimeMins - (cutMins.toIntOrNull() ?: 0)
    } catch (e: Exception) {
        0
    }

    return res
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
    val hour = totalStudyTimeMins / 60
    val mins = totalStudyTimeMins % 60

    return if (mins.toString().length > 1) "${hour}h ${mins}m" else "${hour}h 0${mins}m"
}

fun totalStudyTimeRes(
    startHour: String,
    startMin: String,
    endHour: String,
    endMin: String,
    cutMins: String,
    existingStudyTimeMins: Int
): String {
    val res = totalStudyTimeFormatted(
        totalStudyTimeMins(
            currentStudyMins(
                startHour = startHour,
                startMin = startMin,
                endHour = endHour,
                endMin = endMin,
                cutMins = cutMins
            ), existingStudyTimeMins
        )
    )

    return res
}

// from 1h 01m to 61m
fun convertStringTotalTimeToInt(totalTime: String?): Int {
    val transformedTime = totalTime?.split(" ")
    val hourToMin = (transformedTime?.get(0)?.dropLast(1)?.toIntOrNull() ?: 0) * 60
    val mins = transformedTime?.get(1)?.dropLast(1)?.toIntOrNull() ?: 0

    return hourToMin + mins
}