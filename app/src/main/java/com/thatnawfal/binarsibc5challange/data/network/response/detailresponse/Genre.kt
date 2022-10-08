package com.thatnawfal.binarsibc5challange.data.network.response.detailresponse


import com.google.gson.annotations.SerializedName

data class Genre(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?
)