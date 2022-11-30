package de.dralli.workoutlist.listadapters

import android.content.Context
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import de.dralli.workoutlist.R
import de.dralli.workoutlist.dataobjects.WorkoutItem

class WorkoutItemAdapter(private val context: Context, private val arrayList: java.util.ArrayList<WorkoutItem>) : BaseAdapter() {
    private lateinit var name: TextView
    private lateinit var weight: EditText
    private lateinit var reps: TextView
    private lateinit var sets: TextView
    private lateinit var done: Switch


    override fun getCount(): Int {
        return arrayList.size
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        var convertView = LayoutInflater.from(context).inflate(R.layout.workoutitems_adapter, parent, false)
        name = convertView.findViewById(R.id.workout_item_name)
        weight = convertView.findViewById(R.id.workout_item_weight)
        reps = convertView.findViewById(R.id.workout_item_reps)
        sets = convertView.findViewById(R.id.workout_item_sets)
        done = convertView.findViewById(R.id.workout_item_done)

        var workoutItem = arrayList[position]
        name.text = workoutItem.name
        weight.text = Editable.Factory.getInstance().newEditable(workoutItem.weight?.toString())
        sets.text = "Sets: "+workoutItem.sets
        reps.text = "Reps: "+workoutItem.minReps + " - "+ workoutItem.maxReps
        
        return convertView
    }
}