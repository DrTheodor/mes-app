package dev.drtheo.mes.model.schedule


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Details(
    @SerialName("additional_materials")
    val additionalMaterials: List<AdditionalMaterial>,
//    @SerialName("content")
//    val content: List<Any>,
    @SerialName("lessonId")
    val lessonId: Int,
    @SerialName("lesson_topic")
    val lessonTopic: String,
    @SerialName("materials")
    val materials: List<Material>,
    @SerialName("theme")
    val theme: Theme
)