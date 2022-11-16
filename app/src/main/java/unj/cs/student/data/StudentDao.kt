package unj.cs.student.data
import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface StudentDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(student: Student)

    @Update
    fun update(student: Student)

    @Delete
    fun delete(student: Student)

    @Query("SELECT * from student")
    fun getAll(): Flow<List<Student>>

    @Query("SELECT * from student WHERE _id = :id")
    fun getStudentById(id: Int): Flow<List<Student>>

    @Query("SELECT * from student WHERE ids = :ids")
    fun getStudentByIds(ids: String): Flow<List<Student>>

    @Query("SELECT * from student WHERE name = :name")
    fun getStudentById(name: String): Flow<List<Student>>


}