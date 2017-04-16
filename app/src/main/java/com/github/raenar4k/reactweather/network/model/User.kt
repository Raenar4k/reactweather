package com.github.raenar4k.reactweather.network.model

data class User(val name: Name, val gender: String, val nat: String, val picture: Picture) {
    data class Name(val first: String, val last: String)
    data class Picture(val medium: String, val thumbnail: String)
}

