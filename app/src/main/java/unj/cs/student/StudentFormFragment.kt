package unj.cs.student

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.google.android.material.textfield.TextInputLayout
import unj.cs.student.data.Student
import unj.cs.student.databinding.FragmentStudentFormBinding
import kotlin.properties.Delegates
import androidx.navigation.fragment.navArgs

class StudentFormFragment : Fragment() {
    companion object{
        const val INDEX = "index"
        const val BUTTON_TEXT = "button_text"
    }

    private var _binding: FragmentStudentFormBinding? = null
    private val binding get() = _binding!!
    private lateinit var buttonText: String
    private var index by Delegates.notNull<Int>()
    private val navigationArgs: StudentFormFragmentArgs by navArgs()
    private val viewModel: StudentViewModel by activityViewModels{}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            index = it.getInt(INDEX)
            buttonText = it.getString(BUTTON_TEXT).toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentStudentFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            viewModel = viewModel
        }
        val idEditLayout: TextInputLayout = binding.idTextLayout
        val nameEditLayout: TextInputLayout = binding.nameTextLayout
        val idEditText: EditText = binding.idTextEdit
        val nameEditText: EditText = binding.nameTextEdit
        val submitButton: Button = binding.submitButton
//        val listStudents = StudentAdapter.dataset

        // Get text from arguments
        submitButton.text = buttonText
        if (index != -1){
            idEditText.setText(viewModel.studentList.value!![index].ids)
            nameEditText.setText(viewModel.studentList.value!![index].name)
            submitButton.setOnClickListener {
                if(idEditText.text.toString() == "" || nameEditText.text.toString() == ""){
                    idEditLayout.error = "IDs can't be empty!"
                    nameEditLayout.error = "Name can't be empty!"
                    Toast.makeText(context, "IDs and Name can't be empty!", Toast.LENGTH_LONG).show()
                } else {
                    val student = Student(
                        this.navigationArgs.index,
                        idEditText.text.toString(),
                        nameEditText.text.toString()
                    )
                    // listStudents[index] = student
                    //viewModel.updateStudent(index, student)
                    val action: NavDirections = StudentFormFragmentDirections.actionStudentFormFragmentToStudentListFragment()
                    view.findNavController().navigate(action)

                    Toast.makeText(context, "Data was updated!", Toast.LENGTH_LONG).show()
                }

            }
        } else {
            submitButton.setOnClickListener {
                if(idEditText.text.toString() == "" || nameEditText.text.toString() == ""){
                    idEditLayout.error = "IDs can't be empty!"
                    nameEditLayout.error = "Name can't be empty!"
                    Toast.makeText(context, "IDs and Name can't be empty!", Toast.LENGTH_LONG).show()
                } else {
                    val student = Student(
                        this.navigationArgs.index,
                        idEditText.text.toString(),
                        nameEditText.text.toString()
                    )
                    // listStudents.add(student)
                    viewModel.addNewStudent(idEditText.text.toString(),nameEditText.text.toString())
                    val action: NavDirections = StudentFormFragmentDirections.actionStudentFormFragmentToStudentListFragment()
                    view.findNavController().navigate(action)
                    Toast.makeText(context, "${student.name} was added!", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}