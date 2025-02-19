package dev.drtheo.mes.model.profile

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Profile(
    @SerialName("children")
    val children: List<Children>,
    @SerialName("hash")
    val hash: String,
    @SerialName("profile")
    val profile: ProfileData
)