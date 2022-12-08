package de.dralli.workoutlist.listadapters

import android.content.Context
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import de.dralli.workoutlist.R
import de.dralli.workoutlist.datacontrollers.WorkoutListController
import de.dralli.workoutlist.dataobjects.WorkoutItem


class WorkoutItemAdapterView(private val context: Context, private val arrayList: java.util.ArrayList<WorkoutItem>) : BaseAdapter() {
    private lateinit var name: TextView
    private lateinit var weight: EditText
    private lateinit var reps_n_sets: TextView
    private lateinit var done: CheckBox


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
        var convertView = LayoutInflater.from(context).inflate(R.layout.workoutitems_adapter_view, parent, false)
        name = convertView.findViewById(R.id.workout_item_name)
        weight = convertView.findViewById(R.id.workout_item_weight)
        reps_n_sets = convertView.findViewById(R.id.workout_item_reps_n_sets)
        done = convertView.findViewById(R.id.workout_item_done)

        var workoutItem = arrayList[position]
        name.text = workoutItem.name
        weight.text = Editable.Factory.getInstance().newEditable((workoutItem.weight?.toDouble()?.div(100)).toString())
        done.isChecked = workoutItem.done

        done.setOnCheckedChangeListener { _, isChecked ->
            workoutItem.done = isChecked
            WorkoutListController.getInstance()?.save()
        }


        weight.doAfterTextChanged {
            if (it.isNullOrBlank()) {
                modifyText("0")
                return@doAfterTextChanged
            }
            val originalText = it.toString()
            try {
                val weight = (originalText.toDouble()*100).toInt()
                val numberText = weight.toDouble().div(100).toString()
                if (originalText != numberText) {
                    modifyText(numberText)
                }
                workoutItem.weight = weight
            } catch (e: Exception) {
                modifyText("0")
                workoutItem.weight = 0;
            }

            WorkoutListController.getInstance()?.save()
        }

        reps_n_sets.text = "Sets: "+workoutItem.sets+ "\n" +
                "Reps: "+workoutItem.minReps + " - "+ workoutItem.maxReps
        
        return convertView
    }

    private fun modifyText(numberText: String) {
        weight.setText(numberText)
        weight.setSelection(numberText.length)
    }
}