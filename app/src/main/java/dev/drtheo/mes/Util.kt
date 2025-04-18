package dev.drtheo.mes

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


fun Date.formatToMes(): String = SimpleDateFormat("yyyy-MM-dd", Locale.ROOT).format(this)

fun Calendar.formatToMes(): String = SimpleDateFormat("yyyy-MM-dd", Locale.ROOT).format(this.time)

fun String.parseFromMes(): Date = SimpleDateFormat("yyyy-MM-dd", Locale.ROOT).parse(this)!!

fun List<String>.formatHomework(): String = joinToString("\n\n")

fun Calendar.same(other: Calendar): Boolean = this.get(Calendar.YEAR) == other.get(Calendar.YEAR) &&
        this.get(Calendar.DAY_OF_YEAR) == other.get(Calendar.DAY_OF_YEAR)