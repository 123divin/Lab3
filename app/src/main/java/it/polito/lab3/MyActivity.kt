package it.polito.lab3

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class MyActivity : AppCompatActivity() {

private lateinit var viewModel: MyViewModel

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cancel_reservation)

        // Initialize ViewModel
        viewModel = ViewModelProvider(this)[MyViewModel::class.java]

        val dateToCheck= intent.getStringExtra("date")

        println("datae is $dateToCheck")

                if (dateToCheck != null) {
                        var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
                        var date = LocalDate.parse(dateToCheck, formatter)
                        var formatter2= DateTimeFormatter.ofPattern("yyyy-MM-dd")
                        var date2= date.format(formatter2)
                        viewModel.reservationOnDate(2,date2)
                }

        // Find views by their IDs
        val timePicker = findViewById<TimePicker>(R.id.clock_view)
        val suggestionBox = findViewById<EditText>(R.id.suggestion_box)
        val updateButton = findViewById<Button>(R.id.update_button)
        val cancelButton = findViewById<Button>(R.id.cancel_reservation_button)

        // Set up the TimePicker
        timePicker.setIs24HourView(true) // set to true for 24-hour format
        timePicker.setOnTimeChangedListener { view, hourOfDay, minute ->
        viewModel.onTimeChanged(hourOfDay, minute)
        }

        // Set up the suggestion box
       // suggestionBox.addTextChangedListener(object : TextWatcher {
        //override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        // No-op
        //}

        //override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        //viewModel.onSuggestionTextChanged(s.toString())
        //}

       // })

        // Set up the update button
        updateButton.setOnClickListener {
                if (dateToCheck != null){
                        var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
                        var date = LocalDate.parse(dateToCheck, formatter)
                        var formatter2= DateTimeFormatter.ofPattern("yyyy-MM-dd")
                        var date2= date.format(formatter2)

                        val reservation = viewModel.reservationOnDate(2, date2)
                        viewModel.onUpdateButtonClicked(reservation)
                        //returnMyActivity()
                        finish()
                }
        }

        // Set up the cancel button
        cancelButton.setOnClickListener {
                if (dateToCheck != null) {
                        var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
                        var date = LocalDate.parse(dateToCheck, formatter)
                        var formatter2= DateTimeFormatter.ofPattern("yyyy-MM-dd")
                        var date2= date.format(formatter2)

                        println(date2 + "is the real date")

                        viewModel.onCancelButtonClicked(2, date2)
                        //returnMyActivity()
                        finish()




                }
        }

        // Observe changes in the ViewModel
        viewModel.selectedTime.observe(this) { time ->
                // Update the TimePicker to display the selected time
                timePicker.hour = time.hour
                timePicker.minute = time.minute
        }

                viewModel.suggestion.observe(this) { suggestion ->
                        // Update the suggestion box to display the current suggestion
                        suggestionBox.setText(suggestion)
                }

                viewModel.showCancelConfirmationDialog.observe(this) { showDialog ->
                        if (showDialog) {
                                // Display a confirmation dialog when the cancel button is clicked
                                AlertDialog.Builder(this)
                                        .setMessage("Are you sure you want to cancel this reservation?")
                                        .setPositiveButton("Yes") { dialog, which ->
                                                // Handle cancel confirmation here
                                                viewModel.onCancelConfirmed()
                                        }
                                        .setNegativeButton("No", null)
                                        .show()
                        }
                }
        }


        private fun returnMyActivity() {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
        }


}

