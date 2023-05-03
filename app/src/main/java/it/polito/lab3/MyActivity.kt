package it.polito.lab3

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider


class MyActivity : AppCompatActivity() {

private lateinit var viewModel: MyViewModel

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cancel_reservation)

        // Initialize ViewModel
        viewModel = ViewModelProvider(this)[MyViewModel::class.java]

        var dateToCheck= intent.getStringExtra("date")

        println("datae is $dateToCheck")

                if (dateToCheck != null) {
                        viewModel.reservationOnDate(2,dateToCheck)
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
                        val reservation = viewModel.reservationOnDate(2, dateToCheck)
                        viewModel.onUpdateButtonClicked(reservation)
                }
        }

        // Set up the cancel button
        cancelButton.setOnClickListener {
                if (dateToCheck != null) {
                        viewModel.onCancelButtonClicked(2, dateToCheck)
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
        }
