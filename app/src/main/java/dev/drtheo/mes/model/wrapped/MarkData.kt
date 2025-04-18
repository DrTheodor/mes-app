package dev.drtheo.mes.model.wrapped

import dev.drtheo.mes.model.Mark
import dev.drtheo.mes.model.event.Event

data class MarkData(
    val subjectId: Int,
    val subjectName: String,
    val marks: MutableList<Mark> = ArrayList(),
) {

    companion object {
        fun from(events: List<Event>): List<MarkData> {
            val map: MutableMap<Int, MarkData> = HashMap()

            for (event in events) {
                if (event.marks.isNullOrEmpty())
                    continue

                val data = map.getOrPut(event.subjectId) {
                    MarkData(event.subjectId, event.subjectName)
                }

                data.marks.addAll(event.marks)
            }

            return ArrayList(map.values)
        }
    }
}