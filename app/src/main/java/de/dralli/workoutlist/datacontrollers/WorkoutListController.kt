package de.dralli.workoutlist.datacontrollers

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import de.dralli.workoutlist.dataobjects.WorkoutList
import java.util.function.Consumer

class WorkoutListController private constructor(var sharedPreferences: SharedPreferences) {
    private val sharedPreferencesKey = "data.workoutLists"
    private lateinit var workoutLists: ArrayList<WorkoutList>

    init {
        load()
    }

    companion object{
        private var instance: WorkoutListController? = null
        fun getInstance(): WorkoutListController?{
            return instance
        }

        fun getInstance(sharedPreferences: SharedPreferences): WorkoutListController {
            if (instance == null) {
                instance = WorkoutListController(sharedPreferences)
            }
            return instance as WorkoutListController
        }
    }

    fun getWorkoutLists(): ArrayList<WorkoutList>{
        return workoutLists
    }

    fun addWorkoutList(workoutList: WorkoutList){
        workoutLists.add(workoutList)
        save()
    }

    fun changeWorkoutList(position: Int, workoutList: WorkoutList){
        workoutLists[position] = workoutList
        save()
    }

    fun deleteWorkoutList(position: Int){
        workoutLists.removeAt(position)
        save()
    }

    fun getWorkoutList(position: Int?): WorkoutList? {
        if(position == null)
            return null
        return workoutLists[position]
    }


    fun load(){
        workoutLists = ArrayList()
        val gson = Gson()
        if(sharedPreferences.contains(sharedPreferencesKey)){
            gson.fromJson(sharedPreferences.getString(sharedPreferencesKey, ""), ArrayList<LinkedTreeMap<String, Any>>().javaClass).forEach(
                Consumer {
                    workoutLists.add(WorkoutList(it as LinkedTreeMap<String, Any>))
                }
            )
        }
    }

    fun save(){
        sharedPreferences.edit().putString(sharedPreferencesKey, Gson().toJson(workoutLists, workoutLists.javaClass)).apply()
    }
}