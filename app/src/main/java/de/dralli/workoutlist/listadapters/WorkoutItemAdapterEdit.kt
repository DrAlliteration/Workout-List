package de.dralli.workoutlist.listadapters

import android.app.AlertDialog
import android.content.Context
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.snackbar.Snackbar
import de.dralli.workoutlist.R
import de.dralli.workoutlist.datacontrollers.WorkoutListController
import de.dralli.workoutlist.dataobjects.WorkoutItem
import de.dralli.workoutlist.dataobjects.WorkoutList


class WorkoutItemAdapterEdit(private val context: Context, private val workoutList: WorkoutList) : BaseAdapter() {
    private val arrayList: ArrayList<WorkoutItem>

    private lateinit var name: EditText
    private lateinit var weight: EditText
    private lateinit var min_reps: EditText
    private lateinit var max_reps: EditText
    private lateinit var sets: EditText
    private lateinit var deleteButton: Button

    init {
        arrayList = workoutList.workoutItems
    }

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
        var convertView = LayoutInflater.from(context).inflate(R.layout.workoutitems_adapter_edit, parent, false)
        name = convertView.findViewById(R.id.workout_item_name)
        weight = convertView.findViewById(R.id.workout_item_weight)
        min_reps = convertView.findViewById(R.id.workout_item_min_reps)
        max_reps = convertView.findViewById(R.id.workout_item_max_reps)
        sets = convertView.findViewById(R.id.workout_item_sets)
        deleteButton = convertView.findViewById(R.id.workout_item_delete_button)

        val editableFactory = Editable.Factory.getInstance()
        val workoutItem = arrayList[position]
        name.text = editableFactory.newEditable(workoutItem.name)
        weight.text = editableFactory.newEditable((workoutItem.weight?.toDouble()?.div(100)).toString())
        min_reps.text = editableFactory.newEditable(workoutItem.minReps.toString())
        max_reps.text = editableFactory.newEditable(workoutItem.maxReps.toString())
        sets.text = editableFactory.newEditable(workoutItem.sets.toString())

        name.doAfterTextChanged {
            workoutItem.name = it.toString()
        }

        weight.doAfterTextChanged {
            if (it.isNullOrBlank()) {
                modifyText(weight, "0")
                return@doAfterTextChanged
            }
            val originalText = it.toString()
            try {
                val numberWeight = (originalText.toDouble()*100).toInt()
                val numberText = numberWeight.toDouble().div(100).toString()
                if (originalText != numberText) {
                    modifyText(weight, numberText)
                }
                workoutItem.weight = numberWeight
            } catch (e: Exception) {
                modifyText(weight, "0")
                workoutItem.weight = 0;
            }
        }

        sets.doAfterTextChanged {
            val cleanNr = getCleanInt(it)
            modifyText(sets, cleanNr.toString())
            workoutItem.sets = cleanNr
        }

        min_reps.doAfterTextChanged {
            val cleanNr = getCleanInt(it)
            modifyText(min_reps, cleanNr.toString())
            workoutItem.minReps = cleanNr
        }

        max_reps.doAfterTextChanged {
            val cleanNr = getCleanInt(it)
            modifyText(max_reps, cleanNr.toString())
            workoutItem.maxReps = cleanNr
        }

        deleteButton.setOnClickListener { view ->
            val builder = AlertDialog.Builder(convertView.context!!)
            builder.setMessage("Möchtest du das Workout "+name.text+ " wirklich löschen?")
                .setCancelable(false)
                .setPositiveButton("Ja"){ _, _ ->
                    arrayList.removeAt(position)
                    //workoutList.workoutItems = arrayList
                    saveList()
                    this.notifyDataSetChanged()
                    Snackbar.make(view, "Workout "+ name.text + "gelöscht", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
                }
                .setNegativeButton("Nein"){ dialog, _ ->
                    dialog.dismiss()

                }
            builder.create().show()
        }

        return convertView
    }

    private fun getCleanInt(input: Editable?) : Int{
        if(input.isNullOrBlank()) {
            return 0
        }
        return try {
            input.toString().toInt()
        } catch (e: Exception) {
            0
        }
    }

    private fun modifyText(element: EditText, numberText: String) {
        element.setText(numberText)
        element.setSelection(numberText.length)
    }

    private fun saveList(){
        WorkoutListController.getInstance()?.save()
    }
}