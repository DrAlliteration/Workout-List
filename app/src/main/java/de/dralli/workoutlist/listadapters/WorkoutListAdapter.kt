package de.dralli.workoutlist.listadapters

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import de.dralli.workoutlist.R
import de.dralli.workoutlist.datacontrollers.WorkoutListController
import de.dralli.workoutlist.dataobjects.WorkoutList

class WorkoutListAdapter(private val context: Context, private val arrayList: java.util.ArrayList<WorkoutList>) : BaseAdapter() {
    private lateinit var name: TextView
    private lateinit var editButton: Button
    private lateinit var deleteButton: Button
    private lateinit var launchButton: Button
    private lateinit var listController: WorkoutListController

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
        var convertView = convertView
        listController = WorkoutListController.getInstance()!!
        convertView = LayoutInflater.from(context).inflate(R.layout.workoutlist_adapter, parent, false)
        name = convertView.findViewById(R.id.studentName)
        editButton = convertView.findViewById(R.id.workout_list_edit_button)
        deleteButton = convertView.findViewById(R.id.workout_list_delete_button)
        launchButton = convertView.findViewById(R.id.workout_list_launch_button)
        name.text = arrayList[position].title
        editButton.setOnClickListener { view ->
            // Edit Page öffnen

        }
        deleteButton.setOnClickListener { view ->
            val builder = AlertDialog.Builder(convertView.context!!)
            builder.setMessage("Möchtest du das Workout "+name.text+ " wirklich löschen?")
                .setCancelable(false)
                .setPositiveButton("Ja"){ _, _ ->
                    listController.deleteWorkoutList(position)
                    this.notifyDataSetChanged()
                    Snackbar.make(view, "Workout "+ name.text + "gelöscht", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
                }
                .setNegativeButton("Nein"){ dialog, _ ->
                    dialog.dismiss()

                }
            builder.create().show()
        }

        launchButton.setOnClickListener { view ->
            // Workout Starten
            Snackbar.make(view, "Launch für die Position: $position", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        return convertView
    }
}