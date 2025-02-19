package dev.drtheo.mes

import dev.drtheo.mes.data.AppContainer
import dev.drtheo.mes.data.DefaultAppContainer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Date
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.Request

const val CONTINGENT_ID = "11ead94a-f9c1-400c-9876-5ef8b513e3f2"
const val STUDENT_ID = "37069919"
const val USER_ID = "16834427"
const val CONTRACT_ID = "191100387"

const val TOKEN = "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiIzMDQ3MTc5Iiwic2NwIjoib3BlbmlkIHByb2ZpbGUiLCJtc2giOiIxMWVhZDk0YS1mOWMxLTQwMGMtOTg3Ni01ZWY4YjUxM2UzZjIiLCJpc3MiOiJodHRwczpcL1wvc2Nob29sLm1vcy5ydSIsInJvbCI6IiIsInNzbyI6IjcwNmIyNjUxLTk0N2EtNGE4Mi04NWQ3LWNhM2VjZTY0MDhhYyIsImF1ZCI6IjI6MSIsIm5iZiI6MTczOTYyODY0NywiYXRoIjoic3VkaXIiLCJybHMiOiJ7MTpbMTgzOjE2OltdLDUyNTo0NDpbXSwzMDo0OltdLDQwOjE6W10sMjExOjE5OltdLDUzMzo0ODpbXSwyMDoyOltdXX0iLCJleHAiOjE3NDA0OTI2NDEsImlhdCI6MTczOTYyODY0NywianRpIjoiNjYwNjgxMmMtYTMyMy00ODBkLTg2ODEtMmNhMzIzZTgwZGYyIn0.c_mH51YTUl_kKytLpcIMI1gvlXLGECf7pBhaunYQ2LC7bw2reSgfIQFtaQ3pfQ4SyI8Ozz5w18-1npatVl8TJELCKBAx-4y3MAIWhUbrOMc41J26zSCJugtiJD_gTAjvFnNVJgNLI8H_piKmykLeiWV6H-Ezw09TmHnLYhuM1ycdeSEMdJlYT6Aj1auQSSwpxw2aVf_MpXoIr2nL7S6K13iW2Xy6l-U1XLDXQiWOR9NsZff0odzXANPy3u0ys9XASKEc9zHFFQMtcsfZwAZynZA-GJv5SE3NSsE7eoSEAD3A-lJQlRh0HSt36JjPx__j1r8qLir8lR5jQ6juDB35Hg"


class NetworkUnitTest {

    @Test
    fun schedule_isCorrect() {
        /*val container: AppContainer = DefaultAppContainer()

        runBlocking {
            println(container.eventsRepository.getEvents(Date(System.currentTimeMillis())))
        }*/

        val client = OkHttpClient.Builder()
            .build()

        val response = client.newCall(Request.Builder()
            .url("https://school.mos.ru/api/eventcalendar/v1/api/events?begin_date=2025-02-14&end_date=2025-02-14&expand=homework&person_ids=$CONTINGENT_ID")
            .header("Authorization", "Bearer $TOKEN")
            .header("Client-Type", "diary-mobile")
            .header("X-Mes-Role", "student")
            .header("X-Mes-Subsystem", "familymp").build())
            .execute()

        println(response.body?.string())
    }
}
