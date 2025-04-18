package dev.drtheo.mes.model.schedule


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AdditionalMaterial(
    @SerialName("action_id")
    val actionId: Int,
    @SerialName("action_name")
    val actionName: String,
    @SerialName("description")
    val description: String?,
    @SerialName("id")
    val id: Int?,
    @SerialName("selected_mode")
    val selectedMode: String?,
    @SerialName("title")
    val title: String,
    @SerialName("type")
    val type: String,
    @SerialName("type_name")
    val typeName: String,
    @SerialName("urls")
    val urls: List<UrlLike2>,
    @SerialName("uuid")
    val uuid: String?
)