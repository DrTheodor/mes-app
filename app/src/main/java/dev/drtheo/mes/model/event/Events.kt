package dev.drtheo.mes.model.event

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Events(
    //@SerialName("errors")
    //val errors: Any?,
    @SerialName("response")
    val response: List<Event>,
    @SerialName("total_count")
    val totalCount: Int
)