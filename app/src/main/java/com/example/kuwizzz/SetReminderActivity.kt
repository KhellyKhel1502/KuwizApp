package com.example.kuwizzz

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class SetReminderActivity : AppCompatActivity() {

    private lateinit var titleEditText: EditText
    private lateinit var notesEditText: EditText
    private lateinit var setDateButton: Button
    private lateinit var setTimeButton: Button
    private lateinit var saveReminderButton: Button
    private lateinit var finishButton: Button

    private var selectedDate: String? = null
    private var selectedTime: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_reminder)


        titleEditText = findViewById(R.id.titleEditText)
        notesEditText = findViewById(R.id.notesEditText)
        setDateButton = findViewById(R.id.setDateButton)
        setTimeButton = findViewById(R.id.setTimeButton)
        saveReminderButton = findViewById(R.id.saveReminderButton)
        finishButton = findViewById(R.id.finishButton)


        createNotificationChannel()


        setDateButton.setOnClickListener { showDatePicker() }


        setTimeButton.setOnClickListener { showTimePicker() }


        saveReminderButton.setOnClickListener { saveReminder() }


        finishButton.setOnClickListener { finish() }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
            setDateButton.text = selectedDate
        }, year, month, day)

        datePickerDialog.show()
    }

    private fun showTimePicker() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(this, { _, selectedHour, selectedMinute ->

            val amPm = if (selectedHour >= 12) "PM" else "AM"
            val displayHour = if (selectedHour > 12) selectedHour - 12 else selectedHour
            selectedTime = String.format("%02d:%02d %s", displayHour, selectedMinute, amPm)
            setTimeButton.text = selectedTime
        }, hour, minute, true)

        timePickerDialog.show()
    }

    private fun saveReminder() {
        val title = titleEditText.text.toString()
        val notes = notesEditText.text.toString()

        if (title.isEmpty() || selectedDate == null || selectedTime == null) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }


        scheduleNotification(title, notes, selectedDate!!, selectedTime!!)
        Toast.makeText(this, "Reminder Set: $title on $selectedDate at $selectedTime", Toast.LENGTH_LONG).show()


        finish()
    }

    private fun scheduleNotification(title: String, notes: String, date: String, time: String) {

        val calendar = Calendar.getInstance()


        val dateParts = date.split("/")
        val day = dateParts[0].toInt()
        val month = dateParts[1].toInt() - 1 // Month is 0-indexed
        val year = dateParts[2].toInt()


        val timeParts = time.split(":")
        val hour = timeParts[0].toInt()
        val minute = timeParts[1].split(" ")[0].toInt()


        calendar.set(Calendar.HOUR_OF_DAY, if (time.endsWith("PM") && hour < 12) hour + 12 else hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.DAY_OF_MONTH, day)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.YEAR, year)



        intent.putExtra("reminderTitle", title)  // Make sure the key matches
        intent.putExtra("reminderNotes", notes)  // Make sure the key matches


        val pendingIntent = PendingIntent.getBroadcast(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )


        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "reminder_channel",
                "Reminder Notifications",
                NotificationManager.IMPORTANCE_HIGH
            )
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
}
