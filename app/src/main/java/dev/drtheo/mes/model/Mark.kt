package dev.drtheo.mes.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Mark(
    @SerialName("comment")
    val comment: String?,
    @SerialName("is_exam")
    val isExam: Boolean,
    @SerialName("is_point")
    val isPoint: Boolean,
    @SerialName("value")
    val value: String,
    @SerialName("weight")
    val weight: Int
)

fun createDummyMark(comment: String, value: String, weight: Int): Mark {
    return Mark(
        comment = comment,
        isExam = false,
        isPoint = false,
        value = value, weight = weight
    )
}