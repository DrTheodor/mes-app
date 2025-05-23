package dev.drtheo.mes.model.schedule

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Teacher(
    @SerialName("first_name")
    val firstName: String,
    @SerialName("last_name")
    val lastName: String,
    @SerialName("middle_name")
    val middleName: String,
)