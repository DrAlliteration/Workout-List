package de.dralli.workoutlist.dataobjects

import java.util.*
import kotlin.collections.ArrayList

class WorkoutList() {
    lateinit var title : String
    lateinit var workoutItems: ArrayList<WorkoutItem>
    var lastLaunched: Date? = null


    constructor(attributes: Map<String, Any>) : this() {
        title = attributes.get("title") as String
    }

    constructor(title: String) : this() {
        this.title = title
    }
}