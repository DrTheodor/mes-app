package dev.drtheo.mes.model.event

import dev.drtheo.mes.model.Mark
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Event(
    @SerialName("absence_reason_id")
    val absenceReasonId: Long?,
    @SerialName("cancelled")
    val cancelled: Boolean = false,
    @SerialName("finish_at")
    val finishAt: String,
    @SerialName("homework")
    val homework: EventHomework? = null,
    @SerialName("id")
    val id: Long,
    @SerialName("is_missed_lesson")
    val isMissedLesson: Boolean?,
    @SerialName("marks")
    val marks: List<Mark>? = null,
    @SerialName("nonattendance_reason_id")
    val nonattendanceReasonId: Long?,
    @SerialName("replaced")
    val replaced: Boolean? = false,
    @SerialName("room_name")
    val roomName: String?,
    @SerialName("room_number")
    val roomNumber: String?,
    @SerialName("source")
    val source: String,
    @SerialName("source_id")
    val sourceId: String?,
    @SerialName("start_at")
    val startAt: String,
    @SerialName("subject_id")
    val subjectId: Int = -1,
    @SerialName("subject_name")
    val subjectName: String,
) {
    fun room() = "каб. №$roomNumber"

    fun hasHomework(): Boolean = homework != null && !homework.isEmpty()
}

fun createDummyEvent(id: Long, finishAt: String, source: String,
                     startAt: String, homework: EventHomework
): Event {
    return Event(
        absenceReasonId = null,
        finishAt = finishAt,
        homework = homework,
        id = id,
        isMissedLesson = null,
        marks = null,
        nonattendanceReasonId = null,
        replaced = null,
        roomName = null,
        roomNumber = null,
        source = source,
        sourceId = null,
        startAt = startAt,
        subjectId = -1,
        subjectName = "Lorem Ipsum Dolor sit amet Lorem Ipsum",
    )
}