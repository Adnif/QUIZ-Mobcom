package unj.cs.student

import android.app.Application
import unj.cs.student.data.StudentRoomDatabase

class StudentApplication : Application() {
    val database: StudentRoomDatabase by lazy { StudentRoomDatabase.getDatabase(this) }
}