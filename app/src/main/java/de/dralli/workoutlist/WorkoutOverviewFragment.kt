package de.dralli.workoutlist

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import de.dralli.workoutlist.databinding.FragmentWorkoutOverviewBinding
import de.dralli.workoutlist.datacontrollers.WorkoutListController
import de.dralli.workoutlist.listadapters.WorkoutListAdapter

/**
 * [Fragment] containing the Workout-Lists
 */
class WorkoutOverviewFragment : Fragment() {
    private var _binding: FragmentWorkoutOverviewBinding? = null
    private lateinit var listView : ListView
    lateinit var listController: WorkoutListController

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentWorkoutOverviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listController = WorkoutListController.getInstance(view.context.getSharedPreferences("WorkoutList", MODE_PRIVATE))
        listView = view.findViewById(R.id.workout_list)
        renderList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun renderList(){
        listView.adapter = WorkoutListAdapter(listView.context, listController.getWorkoutLists())
    }
}