package unj.cs.student

import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.CreationExtras
import kotlinx.coroutines.launch
import unj.cs.student.data.Student
import unj.cs.student.data.StudentDao
import unj.cs.student.data.StudentRoomDatabase

class StudentViewModel(private val studentDao: StudentDao, private val savedStateHandle: SavedStateHandle) : ViewModel() {
    private val STATE_KEY:String = "studentList"
    var _studentList: MutableLiveData<List<Student>> = savedStateHandle.getLiveData<List<Student>>(STATE_KEY)
    val studentList: LiveData<List<Student>> get() = _studentList

    fun loadStudent(){
        viewModelScope.launch {
            studentDao.getAll().collect(){
                _studentList.value = it
            }
        }
        savedStateHandle[STATE_KEY] = studentList.value
    }

    fun setStudent(student: Student){
        viewModelScope.launch {
            studentDao.update(student)
        }
    }

    fun addStudent(student: Student){
        viewModelScope.launch {
            studentDao.insert(student)
        }
    }

    fun getStudent(pos: Int): Student {
        return studentList.value!![pos]
    }

    fun count(): Int {
        return studentList.value?.let { it.size } ?: run { 0}
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                // Get the Application object from extras
                val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
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