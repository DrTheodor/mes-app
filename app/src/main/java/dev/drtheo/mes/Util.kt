package dev.drtheo.mes

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


fun Date.formatToMes(): String = SimpleDateFormat("yyyy-MM-dd", Locale.ROOT).format(this)

fun String.parseFromMes(): Date = SimpleDateFormat("yyyy-MM-dd", Locale.ROOT).parse(this)!!

fun List<String>.formatHomework(): String = joinToString("\n\n")