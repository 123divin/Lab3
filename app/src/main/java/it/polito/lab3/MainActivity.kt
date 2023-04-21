package it.polito.lab3

import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.stacktips.view.CalendarListener
import com.stacktips.view.CustomCalendarView
import com.stacktips.view.DayDecorator
import com.stacktips.view.DayView
import com.stacktips.view.utils.CalendarUtils
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.Date


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //////

        //Initialize CustomCalendarView from layout
        val calendarView: CustomCalendarView = findViewById(R.id.calendar_view);
        val selectedDateTv: TextView = findViewById(R.id.selected_date);

        //Initialize calendar with date
        val currentCalendar: Calendar = Calendar.getInstance(Locale.getDefault())

        //Show Monday as first date of week
        calendarView.setFirstDayOfWeek(Calendar.MONDAY)

        //Show/hide overflow days of a month
        calendarView.setShowOverflowDate(false)

        //call refreshCalendar to update calendar the view
        calendarView.refreshCalendar(currentCalendar)


        calendarView.setCalendarListener(object : CalendarListener {
            override fun onDateSelected(date: Date?) {
                if (!CalendarUtils.isPastDay(date)) {
                    val df = SimpleDateFormat("dd-MM-yyyy")
                    selectedDateTv.setText("Selected date is " + df.format(date))
                } else {
                    selectedDateTv.setText("Selected date is disabled!")
                }
            }
            override fun onMonthChanged(date: Date?) {
                val df = SimpleDateFormat("MM-yyyy")
                Toast.makeText(this@MainActivity, df.format(date), Toast.LENGTH_SHORT).show()
            }
        })


        //adding calendar day decorators
        val decorators: MutableList<DayDecorator> = ArrayList()
        decorators.add(DisabledColorDecorator())
        decorators.add(ReservationColorDecorator())
        calendarView.decorators = decorators
        calendarView.refreshCalendar(currentCalendar)
    }


    private class DisabledColorDecorator : DayDecorator {
        override fun decorate(dayView: DayView) {
            if (CalendarUtils.isPastDay(dayView.date)) {
                val color: Int = Color.parseColor("#a9afb9")
                dayView.setBackgroundColor(color)
            }
        }
    }

    private class ReservationColorDecorator : DayDecorator {
        override fun decorate(dayView: DayView) {
            val date1: Date = dayView.date

            //TODO : we need to set here the day where we have an active reservation and then its done!
            val date2: Date = Calendar.getInstance().time
            if (date1.day == date2.day && date1.date == date2.date && date1.year ==date2.year && date1.month == date2.month) {
                val color: Int = Color.parseColor("#00ff00")
                dayView.setBackgroundColor(color)
            }
        }
    }
}