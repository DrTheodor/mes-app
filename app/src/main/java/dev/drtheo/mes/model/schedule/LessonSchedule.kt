package dev.drtheo.mes.model.schedule


import dev.drtheo.mes.model.Mark
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LessonSchedule(
    @SerialName("begin_time")
    val beginTime: String,
    @SerialName("begin_utc")
    val beginUtc: Int,
    @SerialName("building_name")
    val buildingName: String,
//    @SerialName("comment")
//    val comment: Any?,
//    @SerialName("control")
//    val control: Any?,
//    @SerialName("course_lesson_type")
//    val courseLessonType: Any?,
//    @SerialName("created_date_time")
//    val createdDateTime: Any?,
    @SerialName("date")
    val date: String,
    @SerialName("details")
    val details: Details,
//    @SerialName("disease_status_type")
//    val diseaseStatusType: Any?,
    @SerialName("end_time")
    val endTime: String,
    @SerialName("end_utc")
    val endUtc: Int,
//    @SerialName("esz_field_id")
//    val eszFieldId: Any?,
//    @SerialName("evaluation")
//    val evaluation: Any?,
//    @SerialName("field_name")
//    val fieldName: Any?,
    @SerialName("homework_presence_status_id")
    val homeworkPresenceStatusId: Int,
//    @SerialName("homework_to_give")
//    val homeworkToGive: Any?,
    @SerialName("id")
    val id: Int,
    @SerialName("is_missed_lesson")
    val isMissedLesson: Boolean,
    @SerialName("is_virtual")
    val isVirtual: Boolean,
    @SerialName("lesson_education_type")
    val lessonEducationType: String,
    @SerialName("lesson_homeworks")
    val lessonHomeworks: List<LessonHomework>,
    @SerialName("lesson_type")
    val lessonType: String,
    @SerialName("marks")
    val marks: List<Mark>,
    @SerialName("plan_id")
    val planId: Long,
//    @SerialName("remote_lesson")
//    val remoteLesson: Any?,
    @SerialName("room_name")
    val roomName: String,
    @SerialName("room_number")
    val roomNumber: String,
    @SerialName("subject_id")
    val subjectId: Long,
    @SerialName("subject_name")
    val subjectName: String,
    @SerialName("teacher")
    val teacher: Teacher,
)