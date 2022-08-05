package io.github.tonyguyot.flagorama.data.remote.model

import com.google.gson.annotations.SerializedName

data class RestCountryName(
    @field:SerializedName("common") val common: String,
    @field:SerializedName("official") val official: String,
    @field:SerializedName("nativeName") val nativeName: Map<String, RestCountryLocalizedName>
)

data class RestCountryLocalizedName(
    @field:SerializedName("common") val common: String,
    @field:SerializedName("official") val official: String
)