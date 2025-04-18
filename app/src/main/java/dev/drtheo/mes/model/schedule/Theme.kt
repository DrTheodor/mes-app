package dev.drtheo.mes.model.schedule

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Theme(
    @SerialName("average_mark")
    val averageMark: String,
    @SerialName("theme_frames")
    val themeFrames: List<Theme>,
    @SerialName("themeIntegrationId")
    val themeIntegrationId: Int,
    @SerialName("title")
    val title: String?
)