package unj.cs.student.data
import androidx.room.*

@Entity(tableName = "student")
data class Student (
    @PrimaryKey(autoGenerate = true)
    var _id: Int = 0,
    @ColumnInfo(name = "ids")
    val ids: String,
    @ColumnInfo(name = "name")
    val name: String){
    constructor(uid: String, name: String): this(0, uid, name)
}