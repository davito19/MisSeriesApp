package com.davito.misseriesapp.model

data class Serie(
    var id : String? = null,
    var name : String? = null,
    var season : String? = null,
    var summary : String? = null,
    var score : Int? = 0,
    var urlPicture: String? = null,
    var isActionSelected: Boolean = false,
    var isAventureSelected: Boolean = false,
    var isSuspensoSelected: Boolean = false,
    var isInfantilSelected: Boolean = false,
    var isPsicodeliaSelected: Boolean = false,
    var isRomanceSelected: Boolean = false
)
