package dev.drtheo.mes.model.wrapped

import dev.drtheo.mes.model.event.Event
import dev.drtheo.mes.model.event.EventHomework

class HomeworkData {

    companion object {
        fun from(events: List<Event>): List<Homework> {
            val map: MutableMap<Int, Homework> = hashMapOf()

            for (event in events) {
                if (event.homework == null || event.homework.isEmpty())
                    continue

                val hw = map.getOrPut(event.subjectId) {
                    Homework(event.subjectId, event.subjectName)
                }

                hw.add(event.homework)
            }

            return ArrayList(map.values)
        }
    }
}

data class Homework(
    val subjectId: Int,
    val subjectName: String,
    var descriptions: MutableList<String> = ArrayList(),
    var totalCount: Int = 0,
) {
    fun add(eventHomework: EventHomework) {
        descriptions.addAll(eventHomework.descriptions)
        totalCount += eventHomework.totalCount
    }
}