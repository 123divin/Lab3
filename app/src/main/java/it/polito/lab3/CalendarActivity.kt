package it.polito.lab3

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.stacktips.view.CalendarListener
import com.stacktips.view.CustomCalendarView
import com.stacktips.view.DayDecorator
import com.stacktips.view.DayView
import com.stacktips.view.utils.CalendarUtils
//import it.polito.lab3.databinding.ActivityMainBinding
import it.polito.lab3.models.Sport
import it.polito.lab3.viewModel.SportViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.Date



class CalendarActivity : AppCompatActivity() {

    val sportsData = ArrayList<Sport>()
    lateinit var sportViewModel: SportViewModel

    //val appdatabase by lazy { AppDatabase.getDatabase(this) }

    lateinit var viewModal: UserReservationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


         viewModal = ViewModelProvider(this)[UserReservationViewModel::class.java]
        viewModal.addReservation(null)
        viewModal.loadReservation("2")

        //Initialize CustomCalendarView from layout
        val calendarView: CustomCalendarView = findViewById(R.id.calendar_view)
        val selectedDateTv: TextView = findViewById(R.id.selected_date)

        //Initialize calendar with date
        val currentCalendar: Calendar = Calendar.getInstance(Locale.getDefault())

        //Show Monday as first date of week
        calendarView.setFirstDayOfWeek(Calendar.MONDAY)

        //Show/hide overflow days of a month
        calendarView.setShowOverflowDate(false)

        //call refreshCalendar to update calendar the view
        calendarView.refreshCalendar(currentCalendar)

        val reservationButton = findViewById<Button>(R.id.select_date)


        calendarView.setCalendarListener(object : CalendarListener {
            override fun onDateSelected(date: Date?) {
                if (!CalendarUtils.isPastDay(date)) {
                    val df = SimpleDateFormat("dd-MM-yyyy")
                    selectedDateTv.setText("Selected date is " + df.format(date))
                    var dateToPas=df.format(date)

                    reservationButton.setOnClickListener{
                        startMyActivity(dateToPas)
                    }
                } else {
                    selectedDateTv.setText("Selected date is disabled!")
                }
            }
            override fun onMonthChanged(date: Date?) {
                val df = SimpleDateFormat("MM-yyyy")
                Toast.makeText(this@CalendarActivity, df.format(date), Toast.LENGTH_SHORT).show()
            }
        })


        //adding calendar day decorators
        val decorators: MutableList<DayDecorator> = ArrayList()
        decorators.add(DisabledColorDecorator())
        decorators.add(ReservationColorDecorator(viewModal))
        calendarView.decorators = decorators
        calendarView.refreshCalendar(currentCalendar)

        sportViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))
            .get( SportViewModel::class.java )

        Log.d("gg", sportViewModel.all.toString())
    }


    override fun onRestart() {
        super.onRestart()
        viewModal.loadReservation("2")
        val calendarView: CustomCalendarView = findViewById(R.id.calendar_view)
        val currentCalendar: Calendar = Calendar.getInstance(Locale.getDefault())
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

    private fun startMyActivity(date: String) {
        val intent = Intent(this, ReservationUpdateActivity::class.java)
        intent.putExtra("date",date)
        startActivity(intent)
    }


    private class ReservationColorDecorator(var viewModal: UserReservationViewModel) : DayDecorator {



        override fun decorate(dayView: DayView) {
            val date1: Date = dayView.date
            val color: Int = Color.parseColor("#00f00f")

            for (item in viewModal.reservationDates.value!!){

                val formatter = SimpleDateFormat("yyyy-MM-dd")
                var dates = formatter.parse(item.reserve_date)
                if(date1.date == dates.date){
                    dayView.setBackgroundColor(color)
                }
            }

        }
    }
}