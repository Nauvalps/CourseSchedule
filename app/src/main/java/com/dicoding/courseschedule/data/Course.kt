package com.dicoding.courseschedule.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dicoding.courseschedule.data.DataCourseName.COL_COURSE_NAME
import com.dicoding.courseschedule.data.DataCourseName.COL_DAY
import com.dicoding.courseschedule.data.DataCourseName.COL_END_TIME
import com.dicoding.courseschedule.data.DataCourseName.COL_ID
import com.dicoding.courseschedule.data.DataCourseName.COL_LECTURER
import com.dicoding.courseschedule.data.DataCourseName.COL_NOTE
import com.dicoding.courseschedule.data.DataCourseName.COL_START_TIME
import com.dicoding.courseschedule.data.DataCourseName.TABLE_NAME

//TODO 1 : Define a local database table using the schema in app/schema/course.json
@Entity(tableName = TABLE_NAME)
data class Course(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COL_ID)
    var id: Int = 0,

    @ColumnInfo(name = COL_COURSE_NAME)
    var courseName: String,

    @ColumnInfo(name = COL_DAY)
    var day: Int,

    @ColumnInfo(name = COL_START_TIME)
    var startTime: String,

    @ColumnInfo(name = COL_END_TIME)
    var endTime: String,

    @ColumnInfo(name = COL_LECTURER)
    var lecturer: String,

    @ColumnInfo(name = COL_NOTE)
    var note: String
)
