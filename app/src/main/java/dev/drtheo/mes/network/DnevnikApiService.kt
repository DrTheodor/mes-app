package dev.drtheo.mes.network

import dev.drtheo.mes.model.event.Events
import dev.drtheo.mes.model.profile.Profile
import dev.drtheo.mes.model.schedule.LessonSchedule
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface DnevnikApiService {

    @GET("api/eventcalendar/v1/api/events")
    suspend fun getEvents(
        //@Header("Authorization") authHeader: String,
        @Header("X-Mes-Subsystem") mesSubsystem: String = "familymp",
        @Header("X-Mes-Role") mesRole: String = "student",
        @Header("Client-Type") clientType: String = "diary-mobile",

        @Query("person_ids") personIds: String,
        @Query("begin_date") beginDate: String,
        @Query("end_date") endDate: String,
        @Query("expand") expandFields: String? = null,
    ): Events

    @GET("api/family/mobile/v1/lesson_schedule_items/{lesson_id}")
    suspend fun lessonSchedule(
        //@Header("auth-token") accessToken: String,
        @Path("lesson_id") lessonId: Long,
        @Query("student_id") studentId: Long,
        @Header("X-Mes-Subsystem") mesSubsystem: String = "familymp"
    ): LessonSchedule

    @GET("api/family/mobile/v1/profile")
    suspend fun getProfile(
        //@Header("auth-token") accessToken: String,
        @Header("X-Mes-Subsystem") mesSubsystem: String = "familymp"
    ): Profile

    @GET("v2/token/refresh")
    suspend fun refreshToken(
        //@Header("Authorization") bearerToken: String
    ): String
}