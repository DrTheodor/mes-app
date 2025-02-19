package dev.drtheo.mes.model.profile

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class School(
    @SerialName("county")
    val county: String,
    @SerialName("global_school_id")
    val globalSchoolId: Long,
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String,
    @SerialName("phone")
    val phone: String,
    @SerialName("principal")
    val principal: String,
    @SerialName("short_name")
    val shortName: String
)