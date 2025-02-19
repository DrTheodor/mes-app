package dev.drtheo.mes.model.event

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EventHomework(
    @SerialName("descriptions")
    val descriptions: List<String>,
    @SerialName("entries")
    val entries: List<Entry>?,
    @SerialName("execute_count")
    val executeCount: Int?,
//    @SerialName("link_types")
//    val linkTypes: Any?,
    @SerialName("materials")
    val materials: Materials?,
    //@SerialName("presence_status_id")
    //val presenceStatusId: Long,
    //@SerialName("total_count")
    //val totalCount: Int
) {
    fun isEmpty(): Boolean = descriptions.isEmpty() && entries == null && executeCount == null && materials == null
}

fun createDummyHomework(descriptions: List<String>): EventHomework {
    return EventHomework(
        descriptions = descriptions,
        entries = null,
        executeCount = null,
//        linkTypes = null,
        materials = null,
    )
}

@Serializable
data class Entry(
//    @SerialName("attachment_ids")
//    val attachmentIds: List<Any>,
//    @SerialName("attachments")
//    val attachments: List<Any>,
    @SerialName("date_assigned_on")
    val dateAssignedOn: String,
    @SerialName("date_prepared_for")
    val datePreparedFor: String,
    @SerialName("description")
    val description: String,
    @SerialName("duration")
    val duration: Int,
    @SerialName("homework_entry_id")
    val homeworkEntryId: Long,
    @SerialName("materials")
    val materials: String?,
//    @SerialName("student_ids")
//    val studentIds: Any?
)

@Serializable
data class Materials(
    @SerialName("count_execute")
    val countExecute: Int,
    @SerialName("count_learn")
    val countLearn: Int
)

@Serializable
data class Material(
    @SerialName("isHiddenFromStudents")
    val isHiddenFromStudents: Boolean,
    @SerialName("learningTargets")
    val learningTargets: LearningTargets,
    @SerialName("uuid")
    val uuid: String
)

@Serializable
data class LearningTargets(
    @SerialName("forHome")
    val forHome: Boolean,
    @SerialName("forLesson")
    val forLesson: Boolean
)