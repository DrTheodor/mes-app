package dev.drtheo.mes.model.event

import dev.drtheo.mes.model.Mark
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Event(
    @SerialName("absence_reason_id")
    val absenceReasonId: Long?,
    //@SerialName("activities")
    //val activities: Any?,
    //@SerialName("address")
    //val address: Any?,
    //@SerialName("attendances")
    //val attendances: Any?,
    @SerialName("author_id")
    val authorId: String?,
    //@SerialName("author_name")
    //val authorName: Any?,
    @SerialName("building_id")
    val buildingId: Long?,
    @SerialName("building_name")
    val buildingName: String?,
    @SerialName("cancelled")
    val cancelled: Boolean?,
    //@SerialName("city_building_name")
    //val cityBuildingName: Any?,
    @SerialName("class_unit_ids")
    val classUnitIds: List<Int>?,
    @SerialName("class_unit_name")
    val classUnitName: String?,
    //@SerialName("comment")
    //val comment: Any?,
    //@SerialName("comment_count")
    //val commentCount: Any?,
    //@SerialName("comments")
    //val comments: Any?,
    @SerialName("conference_link")
    val conferenceLink: String?,
    //@SerialName("contact_email")
    //val contactEmail: Any?,
    //@SerialName("contact_name")
    //val contactName: Any?,
    //@SerialName("contact_phone")
    //val contactPhone: Any?,
    //@SerialName("control")
    //val control: Any?,
    //@SerialName("course_lesson_type")
    //val courseLessonType: Any?,
    @SerialName("created_at")
    val createdAt: String?,
    @SerialName("description")
    val description: String?,
    @SerialName("esz_field_id")
    val eszFieldId: Long?,
    //@SerialName("external_activities_type")
    //val externalActivitiesType: Any?,
    @SerialName("finish_at")
    val finishAt: String,
    //@SerialName("format_name")
    //val formatName: Any?,
    @SerialName("group_id")
    val groupId: Long?,
    @SerialName("group_name")
    val groupName: String?,
//    @SerialName("health_status")
//    val healthStatus: Any?,
    @SerialName("homework")
    val homework: EventHomework?,
    @SerialName("id")
    val id: Long,
    @SerialName("is_all_day")
    val isAllDay: Boolean?,
    //@SerialName("is_metagroup")
    //val isMetagroup: Any?,
    @SerialName("is_missed_lesson")
    val isMissedLesson: Boolean?,
    @SerialName("journal_fill")
    val journalFill: Boolean?,
    //@SerialName("lesson_education_type")
    //val lessonEducationType: Any?,
    @SerialName("lesson_name")
    val lessonName: String?,
    @SerialName("lesson_theme")
    val lessonTheme: String?,
    @SerialName("lesson_type")
    val lessonType: String?,
//    @SerialName("link_to_join")
//    val linkToJoin: Any?,
    @SerialName("marks")
    val marks: List<Mark>?,
    @SerialName("materials")
    val materials: List<Material>?,
//    @SerialName("need_document")
//    val needDocument: Any?,
    @SerialName("nonattendance_reason_id")
    val nonattendanceReasonId: Long?,
    @SerialName("outdoor")
    val outdoor: Boolean?,
    @SerialName("place")
    val place: String?,
//    @SerialName("place_comment")
//    val placeComment: Any?,
//    @SerialName("place_latitude")
//    val placeLatitude: Any?,
//    @SerialName("place_longitude")
//    val placeLongitude: Any?,
//    @SerialName("place_name")
//    val placeName: Any?,
//    @SerialName("registration_end_at")
//    val registrationEndAt: Any?,
//    @SerialName("registration_start_at")
//    val registrationStartAt: Any?,
    @SerialName("replaced")
    val replaced: Boolean?,
    @SerialName("replaced_teacher_id")
    val replacedTeacherId: Long?,
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
//    @SerialName("student_count")
//    val studentCount: Any?,
    @SerialName("subject_id")
    val subjectId: Long?,
    @SerialName("subject_name")
    val subjectName: String?,
    @SerialName("title")
    val title: String?,
//    @SerialName("type")
//    val type: Any?,
//    @SerialName("types")
//    val types: List<Any>?,
    @SerialName("updated_at")
    val updatedAt: String?,
//    @SerialName("url")
//    val url: Any?,
//    @SerialName("visible_fake_group")
//    val visibleFakeGroup: Any?
) {

    fun name(): String = subjectName ?: title ?: "???"
}

fun createDummyEvent(id: Long, finishAt: String, source: String,
                     startAt: String, title: String, homework: EventHomework
): Event {
    return Event(
        absenceReasonId = null,
//        activities = null,
//        address = null,
//        attendances = null,
        authorId = null,
//        authorName = null,
        buildingId = null,
        buildingName = null,
        cancelled = null,
//        cityBuildingName = null,
        classUnitIds = null,
        classUnitName = null,
//        comment = null,
//        commentCount = null,
//        comments = null,
        conferenceLink = null,
//        contactEmail = null,
//        contactName = null,
//        contactPhone = null,
//        control = null,
//        courseLessonType = null,
        createdAt = null,
        description = null,
        eszFieldId = null,
//        externalActivitiesType = null,
        finishAt = finishAt,
//        formatName = null,
        groupId = null,
        groupName = null,
//        healthStatus = null,
        homework = homework,
        id = id,
        isAllDay = null,
//        isMetagroup = null,
        isMissedLesson = null,
        journalFill = null,
//        lessonEducationType = null,
        lessonName = null,
        lessonTheme = null,
        lessonType = null,
//        linkToJoin = null,
        marks = null,
        materials = null,
//        needDocument = null,
        nonattendanceReasonId = null,
        outdoor = null,
        place = null,
//        placeComment = null,
//        placeLatitude = null,
//        placeLongitude = null,
//        placeName = null,
//        registrationEndAt = null,
//        registrationStartAt = null,
        replaced = null,
        replacedTeacherId = null,
        roomName = null,
        roomNumber = null,
        source = source,
        sourceId = null,
        startAt = startAt,
//        studentCount = null,
        subjectId = null,
        subjectName = null,
        title = title,
//        type = null,
//        types = null,
        updatedAt = null,
//        url = null,
//        visibleFakeGroup = null
    )
}