package it.polito.lab3.repositories

import androidx.lifecycle.LiveData
import it.polito.lab3.dao.SportDao
import it.polito.lab3.models.Sport

class SportRepository(private val sportDao: SportDao) {

    val all: LiveData<List<Sport>> = sportDao.getAll()


}