package dev.drtheo.mes.model.schedule


import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Material(
    @SerialName("action_id")
    val actionId: Int,
    @SerialName("action_name")
    val actionName: String,
    @SerialName("items")
    val items: List<Item>,
    @SerialName("type")
    val type: String,
    @SerialName("type_name")
    val typeName: String
) {
    /*private enum class Type(val SerialName: String, val icon: ImageVector) {
        Test("test_spec_binding", Icons.Rounded.Checklist),
        Attachment("attachments", Icons.Rounded.Attachment),
        LessonTemplate("lesson_template", Icons.Rounded.NoteAlt),
        AtomicObject("atomic_object", Icons.Rounded.Book),
        GameApp("game_app", Icons.Rounded.SportsEsports)
    }

    val icon
        get() =
            Type.values().firstOrNull { type == it.SerialName }?.icon
                ?: Icons.Rounded.QuestionMark*/
}