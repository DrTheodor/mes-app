package dev.drtheo.mes.model.schedule

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Item(
    @SerialName("author")
    val author: String?,
    @SerialName("description")
    val description: String?,
    @SerialName("file_size")
    val fileSize: Int?,
    @SerialName("id")
    val id: Int,
    @SerialName("is_hidden_from_students")
    val isHiddenFromStudents: Boolean,
    @SerialName("is_necessary")
    val isNecessary: Boolean,
    @SerialName("link")
    val link: String?,
    @SerialName("selected_mode")
    val selectedMode: String?,
    @SerialName("title")
    val title: String,
    @SerialName("uuid")
    val uuid: String?,
)