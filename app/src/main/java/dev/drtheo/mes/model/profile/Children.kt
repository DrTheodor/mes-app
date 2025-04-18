package dev.drtheo.mes.model.profile

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Children(
    @SerialName("birth_date")
    val birthDate: String,
    @SerialName("class_level_id")
    val classLevelId: Long,
    @SerialName("class_name")
    val className: String,
    @SerialName("class_unit_id")
    val classUnitId: Long,
    @SerialName("contingent_guid")
    val contingentGuid: String,
    @SerialName("first_name")
    val firstName: String,
    @SerialName("id")
    val studentId: Long,
    @SerialName("last_name")
    val lastName: String,
    @SerialName("middle_name")
    val middleName: String,
    @SerialName("parallel_curriculum_id")
    val parallelCurriculumId: Long,
    @SerialName("phone")
    val phone: String,
    @SerialName("school")
    val school: School,
    @SerialName("sex")
    val sex: String,
    @SerialName("user_id")
    val userId: Long
)