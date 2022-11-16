package unj.cs.student

import unj.cs.student.StudentViewModel
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import unj.cs.student.databinding.FragmentStudentListBinding

class StudentListFragment : Fragment() {
    //    companion object{
//        const val TOAST_MESSAGE = "toast_message"
//    }
    private var _binding: FragmentStudentListBinding? = null
    private val binding get() = _binding!!
    private lateinit var toastMessage: String
    private val viewModel: StudentViewModel by activityViewModels{
        StudentViewModel.Factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        arguments?.let{
//            toastMessage = it.getString(TOAST_MESSAGE).toString()
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentStudentListBinding.inflate(inflater, container, false)
        val root : View = binding.root

        binding.viewModel = viewModel
        val studentRecyclerView: RecyclerView = binding.studentViewRecycler
        studentRecyclerView.adapter = StudentAdapter(viewModel)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val addButton : FloatingActionButton = binding.addButton

        addButton.setOnClickListener{
            val action: NavDirections = StudentListFragmentDirections.actionStudentListFragmentToStudentFormFragment(-1, "Add Student")
            view.findNavController().navigate(action)
        }
//        if (toastMessage != "null"){
//            Toast.makeText(context, toastMessage, Toast.LENGTH_LONG).show()
//        }
//        toastMessage = "null"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}