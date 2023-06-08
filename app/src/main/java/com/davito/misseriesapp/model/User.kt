package com.davito.misseriesapp.model

data class User(
    var uid: String? = null,
    var name : String? = null,
    var email : String? = null,
    var genre : String? = null,
    var genreFavoritos : String? = null,
    var urlPicture : String? = null
)
