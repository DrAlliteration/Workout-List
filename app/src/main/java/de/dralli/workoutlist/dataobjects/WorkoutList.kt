package de.dralli.workoutlist.dataobjects

import com.google.gson.internal.LinkedTreeMap
import java.util.*
import java.util.function.Consumer
import kotlin.collections.ArrayList

data class WorkoutList(
    var title : String?,
    var workoutItems: ArrayList<WorkoutItem>,
    var lastLaunched: Date?
){
    constructor(attributes: Map<String, Any>) : this(null, ArrayList(), null){
        title = attributes.get("title") as String?
        lastLaunched = attributes.get("lastLaunched") as Date?
        val tmpItems = attributes.get("workoutItems") as ArrayList<*>?
        tmpItems?.forEach(
            Consumer {
                workoutItems.add(WorkoutItem(it as LinkedTreeMap<String, Any>))
            }
        )
    }
}