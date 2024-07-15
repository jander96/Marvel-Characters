package com.devj.dcine.data

data class MarvelResponse(
    val data : Data
)

data class  Data (
    val offset : Int,
    val limit : Int,
    val total : Int,
    val count : Int,
    val results : List<MarvelCharacter>
)


data class MarvelCharacter(
    val id : Int,
    val name: String,
    val thumbnail : Thumbnail
)

data class Thumbnail(
    val path : String,
    val extension : String
)