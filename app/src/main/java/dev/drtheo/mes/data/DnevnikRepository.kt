package dev.drtheo.mes.data

import dev.drtheo.mes.model.event.Events
import dev.drtheo.mes.model.profile.Profile
import dev.drtheo.mes.network.DnevnikApiService

interface DnevnikRepository {
    suspend fun getEvents(profile: Profile, beginDate: String, endDate: String = beginDate, expandFields: String? = null): Events
    suspend fun getProfile(): Profile
}

class NetworkDnevnikRepository(
    private val coreApiService: DnevnikApiService,
) : DnevnikRepository {

    override suspend fun getEvents(profile: Profile, beginDate: String, endDate: String, expandFields: String?): Events = coreApiService.getEvents(
        personIds = profile.children[0].contingentGuid, expandFields = expandFields,
        beginDate = beginDate, endDate = endDate
    )

    override suspend fun getProfile(): Profile = coreApiService.getProfile()
}