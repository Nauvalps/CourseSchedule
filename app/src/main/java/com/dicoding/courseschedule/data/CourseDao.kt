package com.dicoding.courseschedule.data

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery

//TODO 2 : Define data access object (DAO)

@Dao
interface CourseDao {

    @RawQuery(observedEntities = [Course::class])
    fun getNearestSchedule(query: SupportSQLiteQuery): LiveData<Course?>

    @RawQuery(observedEntities = [Course::class])
    fun getAll(query: SupportSQLiteQuery): DataSource.Factory<Int, Course>

    @Query("SELECT * FROM course where id = :id")
    fun getCourse(id: Int): LiveData<Course>


    @Query("SELECT * FROM course where day = (strftime('%w', 'now') + 1)\n" +
            "                 AND strftime('%H:%M', startTime) > strftime('%H:%M', 'now')\n" +
            "                 ORDER BY strftime('%H:%M', startTime)")
    fun getTodaySchedule(): List<Course>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(course: Course)

    @Delete
    fun delete(course: Course)


//    fun sort(params: String): DataSource.Factory<Int, Course>
}