package dev.drtheo.mes.model.profile

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Representative(
//    @SerialName("birth_date")
//    val birthDate: Any?,
//    @SerialName("contract_id")
//    val contractId: Any?,
    @SerialName("email")
    val email: String,
    @SerialName("first_name")
    val firstName: String,
    @SerialName("id")
    val id: Long,
    @SerialName("last_name")
    val lastName: String,
    @SerialName("middle_name")
    val middleName: String,
    @SerialName("phone")
    val phone: String,
//    @SerialName("sex")
//    val sex: Any?,
    @SerialName("snils")
    val snils: String,
//    @SerialName("type")
//    val type: Any?,
//    @SerialName("user_id")
//    val userId: Any?
)