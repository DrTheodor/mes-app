package dev.drtheo.mes.model.profile

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Section(
    @SerialName("id")
    val id: Long,
    @SerialName("is_fake")
    val isFake: Boolean,
    @SerialName("name")
    val name: String,
//    @SerialName("subject_id")
//    val subjectId: Any?
)