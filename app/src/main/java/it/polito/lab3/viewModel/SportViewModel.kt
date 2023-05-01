package it.polito.lab3.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import it.polito.lab3.AppDatabase
import it.polito.lab3.models.Sport
import it.polito.lab3.repositories.SportRepository

class SportViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: SportRepository

    val all: LiveData<List<Sport>>

    init {
        val dao = AppDatabase.getDatabase(application).sportDao()
        repository = SportRepository(dao)
        all = repository.all
    }

}