package dev.drtheo.mes.model.schedule


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UrlLike2(
    @SerialName("type")
    val type: String,
    @SerialName("url")
    val url: String
)