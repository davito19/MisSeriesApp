package com.davito.misseriesapp.ui.misseries

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davito.misseriesapp.data.ResourceRemote
import com.davito.misseriesapp.data.SeriesRepository
import com.davito.misseriesapp.model.Serie
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.launch

class MisSeriesViewModel : ViewModel() {

    private val seriesRepository = SeriesRepository()
    private var serieList : ArrayList<Serie> = ArrayList()

    private val _seriesList: MutableLiveData<ArrayList<Serie>> = MutableLiveData()
    val seriesList: LiveData<ArrayList<Serie>> = _seriesList

    private val _errorMsg : MutableLiveData<String?> = MutableLiveData()
    val errorMsg : LiveData<String?> = _errorMsg

    fun loadSeries() {
        serieList.clear()
        viewModelScope.launch {
            val result = seriesRepository.loadSeries()
            result.let { resourceRemote ->
                when(resourceRemote) {
                    is ResourceRemote.Success -> {
                        resourceRemote.data?.documents?.forEach{document ->
                            val serie = document.toObject<Serie>()
                            serie?.let { serieList.add(it) }
                        }
                        _seriesList.postValue(serieList)
                    }
                    is ResourceRemote.Error -> {
                        _errorMsg.postValue(result.message)
                    }else -> {

                    }
                }
            }
        }
    }


}