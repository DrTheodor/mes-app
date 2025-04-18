package dev.drtheo.mes.model.wrapped

import dev.drtheo.mes.model.event.Event

data class DnevnikData(
    val events: List<Event>,
    val homework: List<Homework>,
    val marks: List<MarkData>,
) {
}