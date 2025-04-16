package dev.drtheo.mes.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Mark(
    @SerialName("comment")
    val comment: String?,
//    @SerialName("comment_exists")
//    val commentExists: Boolean,
//    @SerialName("control_form_name")
//    val controlFormName: String,
    //@SerialName("criteria")
    //val criteria: List<Criteria>?,
//    @SerialName("id")
//    val id: Long,
    @SerialName("is_exam")
    val isExam: Boolean,
    @SerialName("is_point")
    val isPoint: Boolean,
//    @SerialName("original_grade_system_type")
//    val originalGradeSystemType: String,
//    @SerialName("point_date")
//    val pointDate: Any?,
    @SerialName("value")
    val value: String,
    //@SerialName("values")
    //val values: List<Value>?,
    @SerialName("weight")
    val weight: Int
) /*{
    companion object MarkCompanion {
        fun fromMarkListDate(mark: org.bxkr.octodiary.models.marklistdate.Mark): Mark {
            return mark.run {
                Mark(
                    comment,
                    commentExists,
                    controlFormName,
                    criteria = null,
                    id,
                    isExam,
                    isPoint,
                    originalGradeSystemType,
                    pointDate,
                    value,
                    values = null,
                    weight
                )
            }
        }

        fun fromMarkListSubject(mark: org.bxkr.octodiary.models.marklistsubject.Mark): Mark {
            return mark.run {
                Mark(
                    comment,
                    commentExists,
                    controlFormName,
                    criteria = null,
                    id,
                    isExam,
                    isPoint,
                    originalGradeSystemType,
                    pointDate,
                    value,
                    values = null,
                    weight
                )
            }
        }
    }
}*/

fun createDummyMark(comment: String, value: String, weight: Int): Mark {
    return Mark(
        comment = comment,
//        commentExists = true,
//        controlFormName = "",
//        id = 0L,
        isExam = false,
        isPoint = false,
//        originalGradeSystemType = "",
        value = value, weight = weight
    )
}