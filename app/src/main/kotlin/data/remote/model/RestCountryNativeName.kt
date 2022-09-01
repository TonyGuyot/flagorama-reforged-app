package io.github.tonyguyot.flagorama.data.remote.model

import com.google.gson.annotations.SerializedName

data class RestCountryNativeName(
    @field:SerializedName("common")
    val common: String,

    @field:SerializedName("official")
    val official: String
)