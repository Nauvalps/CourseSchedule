package com.dicoding.courseschedule.ui.add

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.util.TimePickerFragment
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.*

class AddCourseActivity : AppCompatActivity(), View.OnClickListener, AdapterView.OnItemSelectedListener ,TimePickerFragment.DialogTimeListener {

    private lateinit var startTimeCourse: TextView
    private lateinit var endTimeCourse: TextView
    private lateinit var imgBtnStartCourse: ImageButton
    private lateinit var imgBtnEndCourse: ImageButton
    private lateinit var listDay: Spinner
    private lateinit var addCourse: TextInputEditText
    private lateinit var addLecturer: TextInputEditText
    private lateinit var addNote: TextInputEditText
    private lateinit var addCourseViewModel: AddCourseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_course)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        startTimeCourse = findViewById(R.id.start_time)
        endTimeCourse = findViewById(R.id.end_time)
        imgBtnStartCourse = findViewById(R.id.btn_start_time)
        imgBtnEndCourse = findViewById(R.id.btn_end_time)
        listDay = findViewById(R.id.list_day)
        addCourse = findViewById(R.id.add_course)
        addLecturer = findViewById(R.id.add_lecturer)
        addNote = findViewById(R.id.add_note)

        imgBtnStartCourse.setOnClickListener(this)
        imgBtnEndCourse.setOnClickListener(this)
        listDay.onItemSelectedListener = this

        ArrayAdapter.createFromResource(
                this,
                R.array.day,
                R.layout.list_day
        ).also { arrayAdapter ->  
            arrayAdapter.setDropDownViewResource(R.layout.list_day)
            listDay.adapter = arrayAdapter
        }

        val factory = AddViewModelFactory.createFactory(this)
        addCourseViewModel = ViewModelProvider(this, factory).get(AddCourseViewModel::class.java)
        addCourseViewModel.saved.observe(this, {
            Toast.makeText(this, getString(R.string.insert_success_message), Toast.LENGTH_SHORT).show()
            finish()
        })
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_start_time -> {
                val timePickerFragment = TimePickerFragment()
                timePickerFragment.show(supportFragmentManager, START_TIME)
            }
            R.id.btn_end_time -> {
                val timePickerFragment = TimePickerFragment()
                timePickerFragment.show(supportFragmentManager, END_TIME)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_insert -> {
                val course = addCourse.text.toString().trim()
                val day = listDay.selectedItemPosition

                val lecturer = addLecturer.text.toString().trim()
                val startTime = startTimeCourse.text.toString()
                val endTime = endTimeCourse.text.toString()
                val note = addNote.text.toString()
                addCourseViewModel.insertCourse(course, day, startTime, endTime, lecturer, note)

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDialogTimeSet(tag: String?, hour: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)

        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        when(tag) {
            START_TIME -> startTimeCourse.text = dateFormat.format(calendar.time)
            END_TIME -> endTimeCourse.text = dateFormat.format(calendar.time)
        }
    }

    companion object {
        private const val START_TIME = "StartTime"
        private const val END_TIME = "EndTime"
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        parent?.getItemAtPosition(position)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }
}