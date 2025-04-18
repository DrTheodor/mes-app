package dev.drtheo.mes.model.event

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EventHomework(
    @SerialName("descriptions")
    val descriptions: List<String>,
    @SerialName("execute_count")
    val executeCount: Int?,
    @SerialName("materials")
    val materials: Materials?,
    @SerialName("total_count")
    val totalCount: Int
) {
    fun isEmpty(): Boolean = totalCount == 0
}

fun createDummyHomework(descriptions: List<String>): EventHomework {
    return EventHomework(
        descriptions = descriptions,
        executeCount = null,
        materials = null,
        totalCount = descriptions.size
    )
}

@Serializable
data class Materials(
    @SerialName("count_execute")
    val countExecute: Int,
    @SerialName("count_learn")
    val countLearn: Int
)
