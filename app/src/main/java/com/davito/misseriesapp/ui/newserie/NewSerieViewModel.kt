package com.davito.misseriesapp.ui.newserie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davito.misseriesapp.data.ResourceRemote
import com.davito.misseriesapp.data.SeriesRepository
import com.davito.misseriesapp.model.Serie
import kotlinx.coroutines.launch

class NewSerieViewModel : ViewModel() {

    private val seriesRepository = SeriesRepository()

    private val _errorMsg : MutableLiveData<String?> = MutableLiveData()
    val errorMsg : LiveData<String?> = _errorMsg

    private val _createSerieSucces : MutableLiveData<String?> = MutableLiveData()
    val createSerieSucces : LiveData<String?> = _createSerieSucces


    fun validateData(
        name: String,
        season: String,
        summary: String,
        score: String,
        accion: Boolean,
        aventura: Boolean,
        suspenso: Boolean,
        infantil: Boolean,
        psicodelia: Boolean,
        romance: Boolean
    ) {
        if (name.isEmpty() || season.isEmpty() || summary.isEmpty() || score.isEmpty()){
            _errorMsg.value = "todos los campos son requeridos"
        } else {
            viewModelScope.launch{
                val serie = Serie(
                    name = name,
                    season = season,
                    summary = summary,
                    score = score.toInt(),
                    isActionSelected = accion,
                    isAventureSelected = aventura,
                    isInfantilSelected = infantil,
                    isPsicodeliaSelected = psicodelia,
                    isRomanceSelected = romance,
                    isSuspensoSelected = suspenso
                )
                val result = seriesRepository.saveSerie(serie)
                result.let { resourceRemote ->
                    when(resourceRemote){
                        is ResourceRemote.Success -> {
                            _errorMsg.postValue("Series guardada")
                            _createSerieSucces.postValue(result.data)
                        }
                        is ResourceRemote.Error -> {
                            _errorMsg.postValue(result.message)
                        }
                        else -> {

                        }
                    }
                }
            }
        }
    }
}