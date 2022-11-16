package unj.cs.student

import unj.cs.student.StudentViewModel
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import unj.cs.student.data.Student

class StudentAdapter(viewModel: StudentViewModel): RecyclerView.Adapter<StudentAdapter.StudentViewHolder>(){
    private val viewModel: StudentViewModel = viewModel
    init{
        viewModel.loadStudent()
        if(viewModel.studentList.value == null){
            viewModel._studentList.value = mutableListOf<Student>()
        }
    }

    class StudentViewHolder(val view:View): RecyclerView.ViewHolder(view){
        val nameTextView:TextView = view.findViewById<TextView>(R.id.student_name)
        val idTextView:TextView = view.findViewById<TextView>(R.id.student_id)
    }

    override fun getItemCount(): Int {
        return viewModel.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.student_view, parent, false)

        return StudentViewHolder(layout)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student: Student = viewModel.getStudent(position)
        val ids:String = student.ids
        val name:String = student.name
        val _id:Int = student._id
        holder.idTextView.text = ids
        holder.nameTextView.text = name

        holder.itemView.setOnClickListener(){
            val action = StudentListFragmentDirections.actionStudentListFragmentToStudentFormFragment(position, "Update Student")
            holder.view.findNavController().navigate(action)
        }
    }


}