package unj.cs.student.data

import android.content.Context
import androidx.room.Database
import androidx.room.*

@Database(entities = [Student::class], version = 2, exportSchema = false)
abstract class StudentRoomDatabase : RoomDatabase() {
    abstract fun studentDao() : StudentDao

    companion object{
        @Volatile
        private var INSTANCE: StudentRoomDatabase? = null
        fun getDatabase(context: Context): StudentRoomDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StudentRoomDatabase::class.java,
                    "student_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }


}