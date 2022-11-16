package unj.cs.student

import androidx.lifecycle.*
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import kotlinx.coroutines.launch
import unj.cs.student.data.Student
import unj.cs.student.data.StudentDao
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import unj.cs.student.data.StudentRoomDatabase

class StudentViewModel(private val studentDao : StudentDao, private val savedStateHandle: SavedStateHandle) : ViewModel() {

    private val STATE_KEY:String = "student"
    var _studentList: MutableLiveData<List<Student>> = savedStateHandle.getLiveData<List<Student>>(STATE_KEY)
    val studentList: LiveData<List<Student>> get() = _studentList

    fun fullStudent(){
        viewModelScope.launch {
            studentDao.getAll().collect(){
                _studentList.value = it
            }
        }
        savedStateHandle[STATE_KEY] = studentList.value
    }

    private fun insertStudent(student: Student){
        viewModelScope.launch {
            studentDao.insert(student)
        }
    }

    private fun updateStudent(student: Student) {
        viewModelScope.launch {
            studentDao.update(student)
        }
    }

    private fun getNewStudentEntry(ids: String, name: String): Student {
        return Student(
            ids = ids,
            name = name
        )
    }

    fun addNewStudent(ids: String, name: String){
        val newStudent = getNewStudentEntry(ids,name)
        insertStudent(newStudent)
    }

    private fun getUpdatedStudentEntry(
        studentId: Int,
        ids: String,
        name: String
    ) : Student{
        return Student(
            id = studentId,
            ids = ids,
            name = name
        )
    }

    fun updateStudent(studentId: Int, ids: String, name: String)
    {
        val updatedStudent = getUpdatedStudentEntry(studentId, ids, name)
        updateStudent(updatedStudent)
    }

    fun count(): Int {
        return studentList.value?.let { it.size } ?: run { 0}
    }

    fun getStudent(pos: Int): Student{
        return studentList.value!![pos]
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                // Get the Application object from extras
                val application = checkNotNull(extras[APPLICATION_KEY])
                val studentDao = StudentRoomDatabase.getDatabase(application).studentDao()

                // Create a SavedStateHandle for this ViewModel from extras
                val savedStateHandle = extras.createSavedStateHandle()

                return StudentViewModel(
                    studentDao,
                    savedStateHandle
                ) as T
            }
        }
    }

}

