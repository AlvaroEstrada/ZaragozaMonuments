package com.alvaroestrada.zaragozamonuments.data.model

import com.google.gson.annotations.SerializedName


data class MonumentResponse (
    @SerializedName("totalCount") var totalCount : Int,
    @SerializedName("start") var start : Int,
    @SerializedName("rows") var rows : Int,
    @SerializedName("result") var monuments : List<Monument>
)
