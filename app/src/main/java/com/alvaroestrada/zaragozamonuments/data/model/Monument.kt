package com.alvaroestrada.zaragozamonuments.data.model

import com.google.gson.annotations.SerializedName


data class Monument (
    @SerializedName("title") var title : String,
    @SerializedName("description") var description : String,
    @SerializedName("horario") var horario : String,
    @SerializedName("image") var image : String,
    @SerializedName("geometry") var geometry : Geometry
)
