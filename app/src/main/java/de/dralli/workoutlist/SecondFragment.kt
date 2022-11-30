package de.dralli.workoutlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import de.dralli.workoutlist.databinding.FragmentSecondBinding
import de.dralli.workoutlist.datacontrollers.WorkoutListController
import de.dralli.workoutlist.dataobjects.WorkoutItem
import de.dralli.workoutlist.dataobjects.WorkoutList
import de.dralli.workoutlist.listadapters.WorkoutItemAdapter

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private lateinit var listView : ListView
    var workoutListController: WorkoutListController? = null
    var workoutList: WorkoutList? = null


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private fun initWorkoutList(parameters: Bundle?) {
        workoutListController = WorkoutListController.getInstance()
        val index = parameters?.getInt("WorkoutListIndex")
        workoutList = workoutListController?.getWorkoutList(index)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        initWorkoutList(this.arguments)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listView = view.findViewById(R.id.workout_items)
        renderList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun renderList(){
        listView.adapter = WorkoutItemAdapter(listView.context, workoutList?.workoutItems ?: ArrayList<WorkoutItem>())
    }
}