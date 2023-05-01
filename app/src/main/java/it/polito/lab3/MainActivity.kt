package it.polito.lab3

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.stacktips.view.CalendarListener
import com.stacktips.view.CustomCalendarView
import com.stacktips.view.DayDecorator
import com.stacktips.view.DayView
import com.stacktips.view.utils.CalendarUtils
import it.polito.lab3.databinding.ActivityMainBinding
import it.polito.lab3.models.Sport
import it.polito.lab3.viewModel.SportViewModel
import org.json.JSONArray
import org.json.JSONException
import java.io.BufferedReader
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.Date



class MainActivity : AppCompatActivity() {

    val sportsData = ArrayList<Sport>()
    private lateinit var binding: ActivityMainBinding
    lateinit var sportViewModel: SportViewModel




    lateinit var viewModal: UserReservationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        /*
        viewModal= ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[UserReservationViewModel::class.java]*/

         viewModal = ViewModelProvider(this)[UserReservationViewModel::class.java]


        viewModal.addReservation(null)
        viewModal.loadReservation("2")
        //////

//        courtAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, courtData)
//        binding.courtSpinner?.adapter = courtAdapter

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
        decorators.add(ReservationColorDecorator(viewModal))
        calendarView.decorators = decorators
        calendarView.refreshCalendar(currentCalendar)

        sportViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))
            .get( SportViewModel::class.java )

        Log.d("gg", sportViewModel.all.toString())
    }


    private class DisabledColorDecorator : DayDecorator {
        override fun decorate(dayView: DayView) {
            if (CalendarUtils.isPastDay(dayView.date)) {
                val color: Int = Color.parseColor("#a9afb9")
                dayView.setBackgroundColor(color)
            }
        }
    }

    private class ReservationColorDecorator(var viewModal: UserReservationViewModel) : DayDecorator {



        override fun decorate(dayView: DayView) {
            val date1: Date = dayView.date
            val color: Int = Color.parseColor("#00ff00")



            for (item in viewModal.reservationDates.value!!){

                val formatter = SimpleDateFormat("yyyy-MM-dd")
                var dates = formatter.parse(item.reserve_date)
                if(date1.date == dates.date && date1.year ==dates.year && date1.month == dates.month ){
                    dayView.setBackgroundColor(color)
                }
            }

            val date2: Date = Calendar.getInstance().time
            if (date1.day == date2.day && date1.date == date2.date && date1.year ==date2.year && date1.month == date2.month) {
                val color: Int = Color.parseColor("#00f00f")
                dayView.setBackgroundColor(color)
            }
        }
    }
}