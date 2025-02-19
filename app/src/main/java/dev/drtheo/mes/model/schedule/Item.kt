package dev.drtheo.mes.model.schedule

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Item(
//    @SerialName("accepted_at")
//    val acceptedAt: Any?,
    @SerialName("author")
    val author: String?,
//    @SerialName("average_rating")
//    val averageRating: Any?,
//    @SerialName("binding_id")
//    val bindingId: Any?,
//    @SerialName("class_level_ids")
//    val classLevelIds: Any?,
//    @SerialName("content_type")
//    val contentType: Any?,
//    @SerialName("created_at")
//    val createdAt: Any?,
    @SerialName("description")
    val description: String?,
    @SerialName("file_size")
    val fileSize: Int?,
//    @SerialName("for_home")
//    val forHome: Any?,
//    @SerialName("for_lesson")
//    val forLesson: Any?,
//    @SerialName("full_cover_url")
//    val fullCoverUrl: Any?,
//    @SerialName("icon_url")
//    val iconUrl: Any?,
    @SerialName("id")
    val id: Int,
    @SerialName("is_hidden_from_students")
    val isHiddenFromStudents: Boolean,
    @SerialName("is_necessary")
    val isNecessary: Boolean,
    @SerialName("link")
    val link: String?,
//    @SerialName("partner_response")
//    val partnerResponse: Any?,
    @SerialName("selected_mode")
    val selectedMode: String?,
    @SerialName("title")
    val title: String,
//    @SerialName("updated_at")
//    val updatedAt: Any?,
//    @SerialName("urls")
//    val urls: List<UrlLike>,
//    @SerialName("user_name")
//    val userName: Any?,
    @SerialName("uuid")
    val uuid: String?,
//    @SerialName("views")
//    val views: Any?
)