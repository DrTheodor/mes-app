package dev.drtheo.mes.data

import dev.drtheo.mes.formatToMes
import dev.drtheo.mes.model.event.Events
import dev.drtheo.mes.model.profile.Profile
import dev.drtheo.mes.network.DnevnikApiService
import java.util.Date

interface DnevnikRepository {
    suspend fun getEvents(profile: Profile, beginDate: Date, endDate: Date = beginDate, expandFields: String? = null): Events
    suspend fun getProfile(): Profile
}

/**
 * Network Implementation of Repository that fetch mars photos list from marsApi.
 */
class NetworkDnevnikRepository(
    private val coreApiService: DnevnikApiService,
) : DnevnikRepository {

    /** Fetches list of MarsPhoto from marsApi*/
    override suspend fun getEvents(profile: Profile, beginDate: Date, endDate: Date, expandFields: String?): Events = coreApiService.getEvents(
        personIds = profile.children[0].contingentGuid, expandFields = expandFields,
        beginDate = beginDate.formatToMes(), endDate = endDate.formatToMes()
    )

    override suspend fun getProfile(): Profile = coreApiService.getProfile()
}