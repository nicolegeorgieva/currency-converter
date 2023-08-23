package com.example.currencyconverter.domain

fun formatTime(number: Double): String {
    val formattedList = number.toString().split(".")
    val formattedMins = if (formattedList[1].first() == '0')
        formattedList[1] else "${formattedList[1]}0"

    return "${formattedList[0]}h ${formattedMins.take(2)}m"
}

fun totalStudyTime(
    startHour: String,
    startMins: String,
    endHour: String,
    endMins: String,
    calculatedTime: Double
): Double {
    val currentStudyMins = try {
        calculateCurrentTimeOfStudyingInMin(startHour, startMins, endHour, endMins)
    } catch (e: Exception) {
        0
    }

    val currentStudy = try {
        calculateCurrentTimeOfStudying(currentStudyMins)
    } catch (e: Exception) {
        0.0
    }

    return calculateTotalTimeOfStudying(calculatedTime, currentStudy)
}

/**
 * This fun receives the already calculated time of studying (e.g. 1.2) and current time of studying (e.g. 2.2) and
 * returns a Double representing the sum of both values (3.2 in this example, which means 3h 20m).
 */
fun calculateTotalTimeOfStudying(calculatedTime: Double, currentTime: Double): Double {
    return calculatedTime + currentTime
}

/**
 * This fun receives currentMins and cutMins* and returns a Double value representing the currentTimeOfStudying
 * (e.g. 1.2) in which "1" means 1 hour and "2" means 20 minutes. Or total - 1h 20m.
 */
fun calculateCurrentTimeOfStudying(mins: Int, cutMins: Int = 0): Double {
    val reducedMins = mins - cutMins

    val hour = (reducedMins / 60).toString()
    val min = (reducedMins - (hour.toInt() * 60)).toString()

    val res = if (min.toInt() < 10) {
        "$hour.0$min".toDouble()
    } else {
        "$hour.$min".toDouble()
    }

    return res
}

// 15:20 - 16:40 (80m)
/**
 * This fun receives startTime (e.g. 15:20) and endTime (e.g. 16:40) and calculates current time of studying in mins
 * (80 for this example).
 */
fun calculateCurrentTimeOfStudyingInMin(
    startHour: String,
    startMins: String,
    endHour: String,
    endMins: String
): Int {
    val minutesPerHour = 60

    val startHourInMins = startHour.toInt() * minutesPerHour
    val totalStartTimeInMins = startHourInMins + startMins.toInt()

    val endHourInMins = endHour.toInt() * minutesPerHour
    val totalEndTimeInMins = endHourInMins + endMins.toInt()

    return totalEndTimeInMins - totalStartTimeInMins
}