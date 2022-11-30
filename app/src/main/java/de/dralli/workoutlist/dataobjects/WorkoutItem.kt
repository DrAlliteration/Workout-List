package de.dralli.workoutlist.dataobjects

data class WorkoutItem(
    var name: String?,
    var minReps: Int?,
    var maxReps: Int?,
    var sets: Int?,
    var weight: Int?,
    var done: Boolean
) {
    constructor(attributes: Map<String, Any>) : this(null, null, null, null, null, false){
        name = attributes.get("name") as String?
        minReps = (attributes.get("minReps") as Double?)?.toInt()
        maxReps = (attributes.get("maxReps") as Double?)?.toInt()
        sets = (attributes.get("sets") as Double?)?.toInt()
        weight = (attributes.get("weight") as Double?)?.toInt()
        done = false
    }
}